const API_BASE = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

async function deleteAccount() {
    if (!confirm("Weet je zeker dat je je account wilt verwijderen?")) return;
    
    const token = sessionStorage.getItem("jwt");
    if (!token) {
        alert("Je moet ingelogd zijn.");
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/registrations/me`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        });
        
        const data = await res.json();
        
        if (data.ok) {
            sessionStorage.clear();
            alert("Account verwijderd!");
            window.location.href = "/index.html";
        } else {
            alert(data.message || "Fout bij verwijderen.");
        }
    } catch (error) {
        alert("Server niet bereikbaar.");
    }
}

window.deleteAccount = deleteAccount;
