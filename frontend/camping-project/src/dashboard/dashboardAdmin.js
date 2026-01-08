import BookingService from "../booking/service/BookingService.js";

/**
 * Admin-only guard
 */
const role = sessionStorage.getItem("role");
if (role !== "admin") {
    window.location.href = "/index.html";
}

/**
 * Main initialization
 */
document.addEventListener("DOMContentLoaded", async () => {
    console.log("Dashboard loaded");
    try {
        const dashboardData = await getDashboardData();
        updateDashboardStats(dashboardData);
        renderNotifications(dashboardData.recentNotifications || []);
    } catch (error) {
        console.error("Error loading dashboard data:", error);
        showDashboardError();
    }
});

/**
 * Fetch dashboard data
 */
async function getDashboardData() {
    try {
        const bookings = await BookingService.getAllBookings();
        const today = new Date().toISOString().split("T")[0];

        // Count arrivals and departures for today
        const arrivals = bookings.filter(b => b.startDate?.startsWith(today)).length;
        const departures = bookings.filter(b => b.endDate?.startsWith(today)).length;

        // Count new bookings (use createdAt if available)
        const newBookings = bookings.filter(b => b.createdAt?.startsWith(today)).length;

        // Fetch new notifications
        const token = sessionStorage.getItem("jwt");
        const resp = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/notifications/new", {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        const notifications = await resp.json();

        // Count new contact messages
        const newMessages = notifications.filter(n => n.type === "CONTACT_MESSAGE").length;

        return {
            arrivals,
            departures,
            newBookings,
            newMessages,
            recentNotifications: notifications
        };
    } catch (err) {
        console.error("Failed to fetch dashboard data:", err);
        throw err;
    }
}

/**
 * Update dashboard stat numbers
 */
function updateDashboardStats(data) {
    document.querySelector("#new-messages .stat-number").textContent = data.newMessages ?? 0;
    document.querySelector("#new-bookings .stat-number").textContent = data.newBookings ?? 0;
    document.querySelector("#arrivals .stat-number").textContent = data.arrivals ?? 0;
    document.querySelector("#departures .stat-number").textContent = data.departures ?? 0;

    console.log("Stats updated:", data);
}

/**
 * Render notifications list
 */
function renderNotifications(notifications) {
    const list = document.getElementById("notifications-list");
    const noNot = document.getElementById("no-notifications");
    list.innerHTML = "";

    if (!notifications || notifications.length === 0) {
        noNot.style.display = "block";
        return;
    } else {
        noNot.style.display = "none";
    }

    notifications.forEach(n => {
        const li = document.createElement("li");
        li.className = "notification-item";

        const date = new Date(n.date);
        const formattedDate = date.toLocaleString("nl-NL", { dateStyle: 'short', timeStyle: 'short' });

        const typeText = {
            BOOKING: "Boeking",
            BREAD_ORDER: "Broodjesbestelling",
            CONTACT_MESSAGE: "Contactbericht",
            FACILITY: "Faciliteit"
        }[n.type] || n.type;

        li.innerHTML = `
            <div class="notif-main">
                <div class="notif-meta">
                    <strong class="notif-type">${typeText}</strong>
                    <span class="notif-date">${formattedDate}</span>
                </div>
                <div class="notif-desc">${escapeHtml(n.description || '')}</div>
            </div>
            <div class="notif-actions">
                <button class="btn-open" data-id="${n.id}" data-type="${n.type}" data-objectid="${n.objectId}">Openen</button>
                <button class="btn-handle" data-id="${n.id}">Markeer als afgehandeld</button>
            </div>
        `;

        li.querySelector(".btn-open").addEventListener("click", e => {
            const { type, objectid } = e.currentTarget.dataset;
            openNotificationTarget(type, objectid);
        });

        li.querySelector(".btn-handle").addEventListener("click", async e => {
            const id = e.currentTarget.dataset.id;
            await markNotificationHandled(id);
            li.remove();
            if (list.children.length === 0) noNot.style.display = "block";
        });

        list.appendChild(li);
    });
}

/**
 * Navigate to appropriate admin page
 */
function openNotificationTarget(type, objectId) {
    switch (type) {
        case "BOOKING":
            window.location.href = `bookingsAdmin.html?bookingId=${encodeURIComponent(objectId)}`;
            break;
        case "BREAD_ORDER":
            window.location.href = `breadManagement.html?orderId=${encodeURIComponent(objectId)}`;
            break;
        case "CONTACT_MESSAGE":
            window.location.href = `contactAdmin.html?messageId=${encodeURIComponent(objectId)}`;
            break;
        case "FACILITY":
            window.location.href = `facilitiesAdmin.html?facilityId=${encodeURIComponent(objectId)}`;
            break;
        default:
            window.location.href = `notificationsAdmin.html`;
    }
}

/**
 * Mark notification as handled
 */
async function markNotificationHandled(id) {
    const token = sessionStorage.getItem("jwt");
    const resp = await fetch(`http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/notifications/${encodeURIComponent(id)}/handled`, {
        method: "PUT",
        headers: { "Authorization": token ? `Bearer ${token}` : "" }
    });
    if (!resp.ok) console.error("Failed to mark notification as handled:", resp.status);
}

/**
 * Simple XSS escape
 */
function escapeHtml(unsafe) {
    return unsafe
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#039;");
}

/**
 * Show fallback if dashboard cannot load
 */
function showDashboardError() {
    document.querySelectorAll(".stat-number").forEach(el => el.textContent = "—");
    const statsTitle = document.querySelector(".dashboard-stats h2");
    if (statsTitle) {
        statsTitle.textContent = "Statistieken (backend niet bereikbaar)";
        statsTitle.style.color = "red";
    }

    const list = document.getElementById("notifications-list");
    if (list) list.innerHTML = "";
    const noNot = document.getElementById("no-notifications");
    if (noNot) {
        noNot.style.display = "block";
        noNot.textContent = "Meldingen niet beschikbaar (backend niet bereikbaar).";
    }
}
