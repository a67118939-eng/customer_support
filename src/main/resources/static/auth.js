const authState = {
    mode: "login",
    busy: false
};

const byId = id => document.getElementById(id);

document.addEventListener("DOMContentLoaded", () => {
    initRushCinematicAuth();
    bindAuthEvents();
    checkExistingSession();
});

function bindAuthEvents() {
    byId("loginTab").addEventListener("click", () => setMode("login"));
    byId("registerTab").addEventListener("click", () => setMode("register"));
    byId("loginForm").addEventListener("submit", login);
    byId("registerForm").addEventListener("submit", register);
}

function setMode(mode) {
    authState.mode = mode;
    const loginMode = mode === "login";
    byId("loginTab").classList.toggle("active", loginMode);
    byId("registerTab").classList.toggle("active", !loginMode);
    byId("loginTab").setAttribute("aria-selected", String(loginMode));
    byId("registerTab").setAttribute("aria-selected", String(!loginMode));
    byId("loginForm").classList.toggle("active", loginMode);
    byId("registerForm").classList.toggle("active", !loginMode);
    byId("authTitle").textContent = loginMode ? "Welcome back" : "Create your account";
    byId("authSubtitle").textContent = loginMode
        ? "Sign in to manage customer conversations, Real AI answers, FAQs, tickets, and security events."
        : "Create a secure Rush AI Support account. Registered accounts start with standard user access.";
    setMessage("");
}

async function checkExistingSession() {
    try {
        const response = await fetch("/api/auth/me", { credentials: "include" });
        if (response.ok) {
            const user = await response.json();
            saveAuth(user);
            window.location.replace("/index.html");
        }
    } catch {
        // Stay on the login page when no session is available.
    }
}

async function login(event) {
    event.preventDefault();
    if (authState.busy) return;
    setBusy(true);
    try {
        const user = await authRequest("/api/auth/login", {
            email: byId("loginEmail").value,
            password: byId("loginPassword").value
        });
        saveAuth(user);
        setMessage("Signed in successfully. Opening dashboard...", true);
        window.location.replace("/index.html");
    } catch (error) {
        setMessage(error.message);
    } finally {
        setBusy(false);
    }
}

async function register(event) {
    event.preventDefault();
    if (authState.busy) return;
    setBusy(true);
    try {
        const password = byId("registerPassword").value;
        const confirmPassword = byId("confirmPassword").value;
        if (password !== confirmPassword) {
            throw new Error("Passwords do not match.");
        }
        validatePassword(password);
        const user = await authRequest("/api/auth/register", {
            fullName: byId("registerName").value,
            email: byId("registerEmail").value,
            password,
            confirmPassword
        });
        saveAuth(user);
        setMessage("Account created. Opening dashboard...", true);
        window.location.replace("/index.html");
    } catch (error) {
        setMessage(error.message);
    } finally {
        setBusy(false);
    }
}

async function authRequest(url, body) {
    const response = await fetch(url, {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    const data = await readAuthResponse(response);
    if (!response.ok) {
        throw new Error(data?.message || data?.error || `Authentication failed (${response.status}).`);
    }
    return data;
}

async function readAuthResponse(response) {
    const contentType = response.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
        return response.json().catch(() => null);
    }
    const text = await response.text().catch(() => "");
    return text ? { message: text.replace(/<[^>]*>/g, " ").replace(/\s+/g, " ").trim() } : null;
}

function validatePassword(password) {
    if (!password || password.length < 10 || password.length > 128) {
        throw new Error("Password must be between 10 and 128 characters.");
    }
    if (!/[A-Z]/.test(password) || !/[a-z]/.test(password) || !/\d/.test(password) || !/[^A-Za-z0-9]/.test(password)) {
        throw new Error("Password must include uppercase, lowercase, number, and symbol.");
    }
}

function saveAuth(user) {
    if (user?.csrfToken) {
        localStorage.setItem("supportFaqCsrf", user.csrfToken);
    }
    localStorage.setItem("supportFaqUser", JSON.stringify({
        fullName: user.fullName,
        email: user.email,
        role: user.role
    }));
}

function setBusy(busy) {
    authState.busy = busy;
    document.querySelectorAll(".auth-submit").forEach(button => {
        button.disabled = busy;
    });
}

function setMessage(message, success = false) {
    const element = byId("authMessage");
    element.textContent = message;
    element.classList.toggle("success", success);
}


function initRushCinematicAuth() {
    const scene = byId("rushDepthScene");
    const depthItems = document.querySelectorAll("[data-rush-depth]");
    if (!scene && depthItems.length === 0) return;

    let targetX = 0;
    let targetY = 0;
    let currentX = 0;
    let currentY = 0;
    let ticking = false;

    const applyMotion = () => {
        currentX += (targetX - currentX) * 0.08;
        currentY += (targetY - currentY) * 0.08;

        const rotateX = `${(-currentY * 8).toFixed(3)}deg`;
        const rotateY = `${(currentX * 10).toFixed(3)}deg`;
        const moveX = `${(currentX * 22).toFixed(3)}px`;
        const moveY = `${(currentY * 16).toFixed(3)}px`;

        if (scene) {
            scene.style.setProperty("--rush-rotate-x", rotateX);
            scene.style.setProperty("--rush-rotate-y", rotateY);
            scene.style.setProperty("--rush-move-x", moveX);
            scene.style.setProperty("--rush-move-y", moveY);
        }

        depthItems.forEach(item => {
            const depth = Number(item.dataset.rushDepth || 14);
            item.style.setProperty("--rush-rotate-x", rotateX);
            item.style.setProperty("--rush-rotate-y", rotateY);
            item.style.setProperty("--rush-move-x", `${(currentX * depth).toFixed(3)}px`);
            item.style.setProperty("--rush-move-y", `${(currentY * depth * 0.72).toFixed(3)}px`);
        });

        if (Math.abs(targetX - currentX) > 0.001 || Math.abs(targetY - currentY) > 0.001) {
            requestAnimationFrame(applyMotion);
        } else {
            ticking = false;
        }
    };

    const updateTarget = event => {
        const width = window.innerWidth || 1;
        const height = window.innerHeight || 1;
        targetX = (event.clientX / width - 0.5) * 2;
        targetY = (event.clientY / height - 0.5) * 2;
        if (!ticking) {
            ticking = true;
            requestAnimationFrame(applyMotion);
        }
    };

    const resetTarget = () => {
        targetX = 0;
        targetY = 0;
        if (!ticking) {
            ticking = true;
            requestAnimationFrame(applyMotion);
        }
    };

    window.addEventListener("pointermove", updateTarget, { passive: true });
    window.addEventListener("pointerleave", resetTarget);
}
