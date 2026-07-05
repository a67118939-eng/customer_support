package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.AuthRequests.LoginRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AuthRequests.RegisterRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AuthResponse;
import com.supportfaq.customersupportfaqaiagent.entity.AppUser;
import com.supportfaq.customersupportfaqaiagent.exception.BadRequestException;
import com.supportfaq.customersupportfaqaiagent.repository.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {

    public static final String SESSION_USER_ID = "AUTH_USER_ID";
    public static final String SESSION_EMAIL = "AUTH_EMAIL";
    public static final String SESSION_FULL_NAME = "AUTH_FULL_NAME";
    public static final String SESSION_ROLE = "AUTH_ROLE";
    public static final String SESSION_CSRF = "AUTH_CSRF_TOKEN";

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_MINUTES = 15;
    private static final int SESSION_SECONDS = 30 * 60;

    private final AppUserRepository appUserRepository;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(AppUserRepository appUserRepository, AuditLogService auditLogService,
                       InputSanitizer inputSanitizer) {
        this.appUserRepository = appUserRepository;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    @Transactional
    public AuthResponse register(RegisterRequest requestBody, HttpServletRequest request) {
        String fullName = inputSanitizer.requiredText(requestBody.getFullName(), 140, "Full name");
        String email = normalizeEmail(requestBody.getEmail());
        validatePassword(requestBody.getPassword(), requestBody.getConfirmPassword());

        if (appUserRepository.existsByEmailIgnoreCase(email)) {
            throw new BadRequestException("An account with this email already exists.");
        }

        AppUser user = new AppUser();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(requestBody.getPassword()));
        user.setRole("USER");
        AppUser saved = appUserRepository.save(user);

        auditLogService.log("USER_REGISTERED", "MEDIUM",
                "User registered with role " + saved.getRole(), request, "AppUser", saved.getId());
        return startSession(saved, request);
    }

    @Transactional
    public AuthResponse login(LoginRequest requestBody, HttpServletRequest request) {
        String email = normalizeEmail(requestBody.getEmail());
        Optional<AppUser> candidate = appUserRepository.findByEmailIgnoreCase(email);
        if (candidate.isEmpty()) {
            auditLogService.log("LOGIN_FAILED", "MEDIUM", "Login failed for unknown account", request);
            throw new BadRequestException("Invalid email or password.");
        }

        AppUser user = candidate.get();
        if (!user.isEnabled()) {
            throw new BadRequestException("This account is disabled.");
        }
        if (isLocked(user)) {
            auditLogService.log("LOGIN_BLOCKED_LOCKED", "HIGH", "Locked account login attempt", request, "AppUser", user.getId());
            throw new BadRequestException("This account is temporarily locked. Try again later.");
        }
        if (!passwordEncoder.matches(requestBody.getPassword(), user.getPasswordHash())) {
            registerFailedLogin(user, request);
            throw new BadRequestException("Invalid email or password.");
        }

        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(LocalDateTime.now());
        appUserRepository.save(user);
        auditLogService.log("LOGIN_SUCCESS", "LOW", "User logged in", request, "AppUser", user.getId());
        return startSession(user, request);
    }

    public AuthResponse me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_USER_ID) == null) {
            return null;
        }
        return fromSession(session);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            auditLogService.log("LOGOUT", "LOW", "User logged out", request);
            session.invalidate();
        }
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(SESSION_USER_ID) != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object role = session == null ? null : session.getAttribute(SESSION_ROLE);
        return role != null && "ADMIN".equalsIgnoreCase(role.toString());
    }

    private AuthResponse startSession(AppUser user, HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        request.changeSessionId();
        session.setMaxInactiveInterval(SESSION_SECONDS);
        session.setAttribute(SESSION_USER_ID, user.getId());
        session.setAttribute(SESSION_EMAIL, user.getEmail());
        session.setAttribute(SESSION_FULL_NAME, user.getFullName());
        session.setAttribute(SESSION_ROLE, user.getRole());
        session.setAttribute(SESSION_CSRF, newCsrfToken());
        return fromSession(session);
    }

    private AuthResponse fromSession(HttpSession session) {
        return new AuthResponse(
                (Long) session.getAttribute(SESSION_USER_ID),
                String.valueOf(session.getAttribute(SESSION_FULL_NAME)),
                String.valueOf(session.getAttribute(SESSION_EMAIL)),
                String.valueOf(session.getAttribute(SESSION_ROLE)),
                String.valueOf(session.getAttribute(SESSION_CSRF))
        );
    }

    private void registerFailedLogin(AppUser user, HttpServletRequest request) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_MINUTES));
            auditLogService.log("LOGIN_ACCOUNT_LOCKED", "HIGH",
                    "Account temporarily locked after repeated failed login attempts", request, "AppUser", user.getId());
        } else {
            auditLogService.log("LOGIN_FAILED", "MEDIUM",
                    "Failed login attempt " + attempts, request, "AppUser", user.getId());
        }
        appUserRepository.save(user);
    }

    private boolean isLocked(AppUser user) {
        LocalDateTime lockedUntil = user.getLockedUntil();
        if (lockedUntil == null) {
            return false;
        }
        if (LocalDateTime.now().isAfter(lockedUntil)) {
            user.setLockedUntil(null);
            user.setFailedLoginAttempts(0);
            appUserRepository.save(user);
            return false;
        }
        return true;
    }

    private String normalizeEmail(String email) {
        String sanitized = inputSanitizer.plainText(email, 254);
        if (sanitized == null || sanitized.isBlank()) {
            throw new BadRequestException("Email is required.");
        }
        return sanitized.toLowerCase(Locale.ROOT);
    }

    private void validatePassword(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords do not match.");
        }
        if (password.length() < 10 || password.length() > 128) {
            throw new BadRequestException("Password must be between 10 and 128 characters.");
        }
        if (!password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")
                || !password.matches(".*\\d.*")
                || !password.matches(".*[^A-Za-z0-9].*")) {
            throw new BadRequestException("Password must include uppercase, lowercase, number, and symbol.");
        }
    }

    private String newCsrfToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
