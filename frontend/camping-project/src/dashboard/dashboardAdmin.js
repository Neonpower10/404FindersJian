// Admin-only guard
const role = sessionStorage.getItem("role");
if (role !== "admin") {
    window.location.href = "/index.html";
}

/**
 * Handles dashboard initialization and data updates.
 * Fetches live notifications from the backend API and updates the DOM accordingly.
 */
document.addEventListener("DOMContentLoaded", async () => {
    console.log("Dashboard geladen");
    await loadNotifications();

    // Refresh notifications every 10 seconds
    setInterval(loadNotifications, 10000);
});

/**
 * Fetches booking notifications from the backend.
 * Displays them in the recent notifications list.
 */
async function loadNotifications() {
    const token = sessionStorage.getItem("jwt");

    try {
        const response = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/notifications?unread=true", {
            headers: {
                "Accept": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });

        if (!response.ok) {
            throw new Error(`Kan meldingen niet ophalen, status: ${response.status}`);
        }

        const notifications = await response.json();
        const listEl = document.querySelector("#notificationsList");
        listEl.innerHTML = "";

        if (notifications.length === 0) {
            const li = document.createElement("li");
            li.textContent = "Geen nieuwe meldingen";
            listEl.appendChild(li);
            return;
        }

        // Sort notifications by date (latest first)
        notifications.sort((a, b) => new Date(b.date) - new Date(a.date));

        notifications.forEach(n => {
            const li = document.createElement("li");
            li.textContent = `${n.date} | ${n.message} (${n.type})`;
            listEl.appendChild(li);
        });
    } catch (error) {
        console.error("Fout bij laden meldingen:", error);
        const listEl = document.querySelector("#notificationsList");
        listEl.innerHTML = "";
        const li = document.createElement("li");
        li.textContent = "Meldingen kunnen niet worden geladen";
        listEl.appendChild(li);
    }
}
