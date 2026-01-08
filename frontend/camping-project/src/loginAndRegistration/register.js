// ===== Configuration =====
// Base URL for the API - this is where our backend server is running
const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

// ===== DOM Elements =====
// Get references to all the form elements we need
const form      = document.getElementById('registerForm');
const nameInput = document.getElementById('name');
const surname   = document.getElementById('surname');
const email     = document.getElementById('email');
const password  = document.getElementById('password');
const confirm   = document.getElementById('confirmPassword');
const showPass  = document.getElementById('showPass');
const accept    = document.getElementById('acceptTerms');
const errorMsg  = document.getElementById('errorMsg');

// ===== Helper Functions =====
// Function to display error messages to the user
function showError(msg) {
    if (errorMsg) errorMsg.textContent = msg;
}

// Function to clear any error messages
function clearError() {
    if (errorMsg) errorMsg.textContent = '';
}

// Function to check if an email address is valid
// Uses a regular expression (regex) to check the email format
function validEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// ===== Password Visibility Toggle =====
// When user checks/unchecks "show password", toggle between text and password input type
if (showPass) {
    showPass.addEventListener('change', () => {
        const type = showPass.checked ? 'text' : 'password';
        if (password) password.type = type;
        if (confirm)  confirm.type  = type;
    });
}

// ===== Form Submit Handler =====
// This code runs when the user submits the registration form
if (form) {
    form.addEventListener('submit', async (e) => {
        // Prevent the form from submitting normally (which would reload the page)
        e.preventDefault();
        clearError();

        // Get the values from the form inputs and remove extra spaces
        const firstName = nameInput.value.trim();
        const lastName  = surname.value.trim();
        const emailAddr = email.value.trim();
        const pwd       = password.value.trim();
        const pwd2      = confirm.value.trim();

        // ===== Validation =====
        // Check if all fields are filled in
        if (!firstName || !lastName || !emailAddr || !pwd || !pwd2) {
            return showError('Vul alle velden in.');
        }
        
        // Check if email format is valid
        if (!validEmail(emailAddr)) {
            return showError('Vul een geldig e-mailadres in.');
        }
        
        // Check if password is at least 6 characters
        if (pwd.length < 6) {
            return showError('Wachtwoord moet minimaal 6 tekens zijn.');
        }
        
        // Check if both passwords match
        if (pwd !== pwd2) {
            return showError('Wachtwoorden komen niet overeen.');
        }
        
        // Check if user accepted the terms
        if (!accept.checked) {
            return showError('Je moet akkoord gaan met de voorwaarden.');
        }

        // ===== Send Data to Backend =====
        // Create the data object to send to the server
        // The field names must match what the backend expects
        const payload = {
            name: firstName,
            surname: lastName,
            email: emailAddr,
            password: pwd
        };

        try {
            // Send a POST request to the backend API
            // async/await means we wait for the response before continuing
            const res = await fetch(`${API_BASE}/registrations`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)  // Convert object to JSON string
            });

            // Try to parse the response as JSON
            let data = {};
            try {
                data = await res.json();
            } catch {
                // If backend doesn't send JSON, just leave empty
            }

            // Check if the registration was successful
            if (res.ok && (data.ok === true || res.status === 201)) {
                // Show success message about verification email
                // Note: we use showError function but with a positive message
                showError(data.message || 'Registratie gelukt! Check je e-mail voor de verificatielink.');
                
                // Clear the form
                form.reset();
            } else {
                // Show error message from the server
                showError(data.message || 'Registratie mislukt. Probeer het opnieuw.');
            }
        } catch (err) {
            // If there's a network error or server is not reachable
            console.error('Error saving registration:', err);
            showError('Server is niet bereikbaar. Staat de backend aan?');
        }
    });
}
