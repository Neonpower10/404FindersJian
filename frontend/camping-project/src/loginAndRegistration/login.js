const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

// Small toast helper
function showToast(message, type = "success") {
    let toast = document.querySelector(".toast");
    if (!toast) {
        toast = document.createElement("div");
        toast.className = "toast";
        document.body.appendChild(toast);
    }
    toast.textContent = message;
    toast.classList.remove("toast--success", "toast--error");
    toast.classList.add(type === "error" ? "toast--error" : "toast--success");
    toast.classList.add("show");
    setTimeout(() => {
        toast.classList.remove("show");
    }, 3000);
}

// Basic elements
const form = document.getElementById("loginForm");
const errorMsg = document.getElementById("errorMsg");

function showError(msg) {
    if (errorMsg) errorMsg.textContent = msg;
    console.warn("Login error:", msg);
}

function clearError() {
    if (errorMsg) errorMsg.textContent = "";
}

if (form) {
    // show/hide password (checkbox)
    const showPass = document.getElementById("showPass");
    const passwordInput = form.querySelector('input[type="password"]');

    if (showPass && passwordInput) {
        showPass.addEventListener("change", () => {
            passwordInput.type = showPass.checked ? "text" : "password";
        });
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        clearError();

        const emailInput = form.querySelector('input[type="email"]');
        const passInput = form.querySelector('input[type="password"]');

        if (!emailInput || !passInput) {
            showError("Loginformulier is niet goed geladen.");
            return;
        }

        const em = emailInput.value.trim();
        const pw = passInput.value.trim();

        if (!em || !pw) {
            showError("Vul je e-mailadres en wachtwoord in.");
            return;
        }

        const payload = {
            email: em,
            password: pw
        };

        try {
            const res = await fetch(`${API_BASE}/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            let data = null;
            try {
                data = await res.json();
            } catch {
                data = null;
            }

            if (!res.ok || (data && data.ok === false)) {
                const msg =
                    (data && data.message) ||
                    "Onjuiste inloggegevens.";
                showError(msg);
                showToast(msg, "error");
                return;
            }

            // save JWT
            if (data && data.token) {
                window.sessionStorage.setItem("jwt", data.token);
            }

            // save role (admin / user)
            if (data && data.role) {
                window.sessionStorage.setItem("role", data.role);
            } else {
                window.sessionStorage.setItem("role", "user");
            }

            const name = data && (data.name || data.email || em);
            const successMsg = `Succesvol ingelogd als ${name}.`;
            showToast(successMsg, "success");
            clearError();

            // After logging in: admin -> dashboard, user -> home
            const role = data && data.role ? data.role : "user";
            const target =
                role === "admin"
                    ? "/dashboardAdmin.html"
                    : "/index.html";

            window.location.href = target;

        } catch (err) {
            console.error("Error during login:", err);
            const msg = "Server is niet bereikbaar. Probeer het later opnieuw.";
            showError(msg);
            showToast(msg, "error");
        }
    });
} else {
    console.error("No loginForm found");
}
