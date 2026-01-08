const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

async function verifyEmailToken() {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");
    
    if (!token) {
        document.querySelector(".success-icon").textContent = "✗";
        document.querySelector(".success-icon").style.color = "#e74c3c";
        document.querySelector("h2").textContent = "Geen verificatietoken gevonden";
        document.querySelector("p").textContent = "De verificatielink is niet geldig.";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/verify?token=${token}`);
        const data = await res.json();
        
        if (data.ok) {
            document.querySelector(".success-icon").textContent = "✓";
            document.querySelector(".success-icon").style.color = "#27ae60";
            document.querySelector("h2").textContent = "Je e-mail is geverifieerd!";
            document.querySelector("p").textContent = "Je account is nu actief. Je kunt inloggen om verder te gaan.";
        } else {
            document.querySelector(".success-icon").textContent = "✗";
            document.querySelector(".success-icon").style.color = "#e74c3c";
            document.querySelector("h2").textContent = "Verificatie mislukt";
            document.querySelector("p").textContent = data.message || "De verificatielink is niet geldig of al gebruikt.";
        }
    } catch (error) {
        document.querySelector(".success-icon").textContent = "✗";
        document.querySelector(".success-icon").style.color = "#e74c3c";
        document.querySelector("h2").textContent = "Fout";
        document.querySelector("p").textContent = "Server is niet bereikbaar. Probeer het later opnieuw.";
    }
}

verifyEmailToken();



