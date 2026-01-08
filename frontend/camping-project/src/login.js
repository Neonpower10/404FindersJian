// Takes elements from the Document Object Model
const form = document.getElementById('loginForm');
const username = document.getElementById('username');
const password = document.getElementById('password');
const showPass = document.getElementById('showPass');
const errorMsg = document.getElementById('errorMsg');

// Shows or hides password when checkbox is toggled
if (showPass && password) {
  showPass.addEventListener('change', () => {
    password.type = showPass.checked ? 'text' : 'password';
  });
}

// Handles from submission and Simple validation
if (form) {
  form.addEventListener('submit', (e) => {
    e.preventDefault(); // Stop page reload on submit
    const u = username.value.trim();
    const p = password.value.trim();

    // Checks if both fields are filled
    if (!u || !p) {
      errorMsg.textContent = 'Vul je gebruikersnaam en wachtwoord in.';
      return;
    }

    //simple demo login logic
    if (u === 'admin' && p === '1234') {
      errorMsg.textContent = '';
      alert('Succesvol ingelogd!');
      window.location.href = '/dashboard.html'; // Redirect example
    } else {
      errorMsg.textContent = 'Ongeldige gebruikersnaam of wachtwoord.';
    }
  });
}
