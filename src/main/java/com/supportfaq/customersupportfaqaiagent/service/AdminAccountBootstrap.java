package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.entity.AppUser;
import com.supportfaq.customersupportfaqaiagent.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
public class AdminAccountBootstrap implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountBootstrap.class);

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Value("${app.auth.admin.email:admin@support.local}")
    private String adminEmail;

    @Value("${app.auth.admin.full-name:System Admin}")
    private String adminFullName;

    @Value("${app.auth.admin.password:}")
    private String adminPassword;

    public AdminAccountBootstrap(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        String normalizedAdminEmail = normalizeEmail(adminEmail);
        keepOnlyConfiguredAdmin(normalizedAdminEmail);

        AppUser admin = appUserRepository.findByEmailIgnoreCase(normalizedAdminEmail)
                .orElseGet(AppUser::new);
        boolean newAdmin = admin.getId() == null;

        if (newAdmin && adminPassword.isBlank()) {
            logger.warn("No admin account was created because app.auth.admin.password is empty.");
            return;
        }
        if (!adminPassword.isBlank()) {
            validateAdminPassword(adminPassword);
            admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        }

        admin.setEmail(normalizedAdminEmail);
        admin.setFullName(blankToDefault(adminFullName, "System Admin"));
        admin.setRole("ADMIN");
        admin.setEnabled(true);
        admin.setFailedLoginAttempts(0);
        admin.setLockedUntil(null);
        appUserRepository.save(admin);
    }

    private void keepOnlyConfiguredAdmin(String normalizedAdminEmail) {
        for (AppUser user : appUserRepository.findAllByRoleIgnoreCase("ADMIN")) {
            if (!normalizedAdminEmail.equalsIgnoreCase(user.getEmail())) {
                user.setRole("USER");
                appUserRepository.save(user);
            }
        }
    }

    private String normalizeEmail(String email) {
        String normalized = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        if (normalized.isBlank()) {
            throw new IllegalStateException("app.auth.admin.email must not be empty.");
        }
        return normalized;
    }

    private String blankToDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private void validateAdminPassword(String password) {
        if (password.length() < 10 || password.length() > 128
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")
                || !password.matches(".*\\d.*")
                || !password.matches(".*[^A-Za-z0-9].*")) {
            throw new IllegalStateException(
                    "app.auth.admin.password must be 10-128 characters and include uppercase, lowercase, number, and symbol.");
        }
    }
}
