// ===== Config =====
const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

// ===== DOM elements =====
const form      = document.getElementById('registerForm');
const nameInput = document.getElementById('name');
const surname   = document.getElementById('surname');
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

// Wachtwoord tonen/verbergen
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

        const fn  = nameInput.value.trim();
        const ln  = surname.value.trim();
        const em  = email.value.trim();
        const pw  = password.value.trim();
        const pw2 = confirm.value.trim();

        // Validation
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

        // Payload has to match registration.java
        const payload = {
            name: fn,
            surname: ln,
            email: em,
            password: pw
        };

        try {
            const res = await fetch(`${API_BASE}/registrations`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            let data = {};
            try {
                data = await res.json();
            } catch {
                // IF backend doesn't send JSON, just leave empty
            }

            if (res.ok && (data.ok === true || res.status === 201)) {
                alert('Registratie gelukt! Je kunt nu inloggen.');
                window.location.href = '/accountLogin.html';
            } else {
                showError(data.message || 'Registratie mislukt. Probeer het opnieuw.');
            }
        } catch (err) {
            console.error('Error saving registration:', err);
            showError('Server is niet bereikbaar. Staat de backend aan?');
        }
    });
}
