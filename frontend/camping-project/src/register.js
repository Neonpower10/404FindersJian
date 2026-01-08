// ===== Config =====
const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war/api";
const DERIVE_USERNAME_FROM_EMAIL = true;

// ===== Takes elements =====
const form = document.getElementById('registerForm');
const firstName = document.getElementById('firstName');
const lastName  = document.getElementById('lastName');
const email     = document.getElementById('email');
const password  = document.getElementById('password');
const confirm   = document.getElementById('confirmPassword');
const showPass  = document.getElementById('showPass');
const accept    = document.getElementById('acceptTerms');
const errorMsg  = document.getElementById('errorMsg');

// ===== Helpers =====
function showError(msg) {
    if (errorMsg) errorMsg.textContent = msg;
}
function clearError() {
    if (errorMsg) errorMsg.textContent = '';
}
function validEmail(m) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(m);
}

// Show/Hide password (both fields)
if (showPass) {
    showPass.addEventListener('change', () => {
        const type = showPass.checked ? 'text' : 'password';
        if (password) password.type = type;
        if (confirm)  confirm.type  = type;
    });
}

// ===== Submit handler =====
if (form) {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        clearError();

        const fn = firstName.value.trim();
        const ln = lastName.value.trim();
        const em = email.value.trim();
        const pw = password.value.trim();
        const pw2 = confirm.value.trim();

        // Basic validations
        if (!fn || !ln || !em || !pw || !pw2) {
            return showError('Vul alle velden in.');
        }
        if (!validEmail(em)) {
            return showError('Vul een geldig e-mailadres in.');
        }
        if (pw.length < 6) {
            return showError('Wachtwoord moet minimaal 6 tekens zijn.');
        }
        if (pw !== pw2) {
            return showError('Wachtwoorden komen niet overeen.');
        }
        if (!accept.checked) {
            return showError('Je moet akkoord gaan met de voorwaarden.');
        }

        // Building payload for the backend
        const username = DERIVE_USERNAME_FROM_EMAIL
            ? em.split('@')[0]
            : `${fn}.${ln}`.replace(/\s+/g, '').toLowerCase();

        const payload = {
            username,
            email: em,
            password: pw,
        };

        try {
            const res = await fetch(`${API_BASE}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });

            // Try to read JSON; if not JSON, create a fallback
            let data = {};
            try { data = await res.json(); } catch { /* ignore */ }

            if (res.ok && (data.ok === true || res.status === 200 || res.status === 201)) {
                alert('Registratie gelukt! Je kunt nu inloggen.');
                window.location.href = '/login.html';
            } else {
                showError(data.message || 'Registratie mislukt. Probeer het opnieuw.');
            }
        } catch (err) {
            showError('Server niet bereikbaar. Staat de backend aan?');
        }
    });
}
