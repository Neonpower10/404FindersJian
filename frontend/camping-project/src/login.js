
// Grab DOM elements first (must come before usage)
const form = document.getElementById('loginForm');
const username = document.getElementById('username');
const password = document.getElementById('password');
const showPass = document.getElementById('showPass');
const errorMsg = document.getElementById('errorMsg');
const pingBtn = document.getElementById('pingBtn'); // aanwezig als je een testknop hebt

// Show / hide password
if (showPass && password) {
    showPass.addEventListener('change', () => {
        password.type = showPass.checked ? 'text' : 'password';
    });
}

// Submit handler
if (form) {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const u = (username?.value || '').trim();
        const p = (password?.value || '').trim();

        // Client-side validation
        if (!u || !p) {
            errorMsg.textContent = 'Vul je gebruikersnaam en wachtwoord in.';
            return;
        }

        try {
            // Backend call
            const res = await fetch(`${API_BASE}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username: u, password: p }),
            });

            const data = await res.json().catch(() => ({}));

            if (res.ok && data.ok) {
                errorMsg.textContent = '';
                alert('Succesvol ingelogd!');
                // Voorbeeld: token opslaan en door
                // localStorage.setItem('token', data.token);
                window.location.href = '/dashboard.html';
            } else {
                errorMsg.textContent = data.error || 'Ongeldige gebruikersnaam of wachtwoord.';
            }
        } catch (err) {
            console.error(err);
            errorMsg.textContent = 'Kon de server niet bereiken.';
        }
    });
}
