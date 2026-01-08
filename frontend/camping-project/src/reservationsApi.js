const API_BASE =
    "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

export async function getReservations() {
    const res = await fetch(`${API_BASE}/reservations`);
    return res.json();
}

export async function addReservation(reservation) {
    const res = await fetch(`${API_BASE}/reservations`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reservation),
    });
    return res.json();
}

export async function deleteReservation(id) {
    await fetch(`${API_BASE}/reservations/${id}`, { method: "DELETE" });
}
