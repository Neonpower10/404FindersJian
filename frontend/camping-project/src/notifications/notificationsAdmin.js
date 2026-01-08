async function loadNotifications() {
    try {
        const response = await fetch("/api/notifications");
        const notifications = await response.json();

        const container = document.getElementById("notifications");
        container.innerHTML = "";

        if (notifications.length === 0) {
            container.innerHTML = "<p>No notifications yet.</p>";
            return;
        }

        notifications.forEach(n => {
            const div = document.createElement("div");
            div.className = "notification";

            div.innerHTML = `
                <div>${n.message}</div>
                <div class="timestamp">${new Date(n.timestamp).toLocaleString()}</div>
            `;

            container.appendChild(div);
        });
    } catch (err) {
        document.getElementById("notifications").innerHTML =
            "<p style='color:red;'>Failed to load notifications.</p>";
    }
}

// Initial load
loadNotifications();

// Auto-refresh every 3 seconds
setInterval(loadNotifications, 3000);
