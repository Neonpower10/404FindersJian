// Admin-only guard
const role = sessionStorage.getItem("role");
if (role !== "admin") {
    window.location.href = "/index.html";
}

/**
 * Handles dashboard initialization and data updates.
 * Fetches live data from the backend API and updates the DOM accordingly.
 */

document.addEventListener("DOMContentLoaded", async () => {
    console.log("Dashboard geladen");

    try {
        const dashboardData = await getDashboardData();
        updateDashboardStats(dashboardData);
    } catch (error) {
        console.error("Fout bij het ophalen van dashboardgegevens:", error);
        showDashboardError();
    }
});

/**
 * Fetches dashboard data from the backend API.
 * @returns {Promise<Object>} Dashboard statistics from the backend
 */
async function getDashboardData() {
    const token = sessionStorage.getItem("jwt");

    const response = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/dashboard", {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Authorization": token ? `Bearer ${token}` : ""
        }
    });

    if (!response.ok) {
        throw new Error(`Server returned ${response.status}`);
    }

    return await response.json();
}

/**
 * Updates the dashboard statistics on the page.
 * @param {Object} data - Dashboard data from the backend
 */
function updateDashboardStats(data) {
    document.querySelector("#new-messages .stat-number").textContent = data.newMessages;
    document.querySelector("#new-bookings .stat-number").textContent = data.newBookings;
    document.querySelector("#arrivals .stat-number").textContent = data.arrivals;
    document.querySelector("#departures .stat-number").textContent = data.departures;

    console.log("Statistieken bijgewerkt:", data);
}

/**
 * Displays a fallback message when the backend data cannot be loaded.
 */
function showDashboardError() {
    document.querySelectorAll(".stat-number").forEach(el => {
        el.textContent = "—";
    });

    const statsTitle = document.querySelector(".dashboard-stats h2");
    if (statsTitle) {
        statsTitle.textContent = "Statistieken (backend niet bereikbaar)";
        statsTitle.style.color = "red";
    }
}
