const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/reservations";

let reservations = [];
let selectedReservation = null;

async function fetchReservations() {
    const res = await fetch(API_URL, {
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
        }
    });
    reservations = await res.json();
    renderList(reservations);
}

function renderList(list) {
    const ul = document.getElementById("reservationList");
    ul.innerHTML = "";

    list.forEach(r => {
        const li = document.createElement("li");
        li.innerHTML = `
            <strong>Reservering #${r.id}</strong><br>
            Aantal: ${r.productAmount}<br>
            <button onclick="edit(${r.id})">Bewerk</button>
            <button onclick="view(${r.id})">Bekijk</button>
            <button class="danger" onclick="cancel(${r.id})">
              Reservering annuleren
            </button>
        `;
        ul.appendChild(li);
    });
}

function view(id) {
    selectedReservation = reservations.find(r => r.id === id);
    renderDetail(false);
}

function edit(id) {
    selectedReservation = reservations.find(r => r.id === id);
    renderDetail(true);
}

function renderDetail(editable) {
    const d = selectedReservation;
    const div = document.getElementById("reservationDetail");

    div.innerHTML = `
        <label>Startdatum</label>
        <input type="date" id="start" value="${d.reservationStartDate}"
               ${editable ? "" : "disabled"}>

        <label>Einddatum</label>
        <input type="date" id="end" value="${d.reservationEndDate}"
               ${editable ? "" : "disabled"}>

        <label>Aantal</label>
        <input type="number" id="amount" value="${d.productAmount}"
               ${editable ? "" : "disabled"}>

        ${editable ? `<button onclick="save(${d.id})">Opslaan</button>` : ""}
    `;
}

async function save(id) {
    const body = {
        reservationStartDate: document.getElementById("start").value,
        reservationEndDate: document.getElementById("end").value,
        productAmount: parseInt(document.getElementById("amount").value)
    };

    await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
        },
        body: JSON.stringify(body)
    });

    fetchReservations();
}

async function cancel(id) {
    if (!confirm("Weet je zeker dat je deze reservering wilt annuleren?")) return;

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
        }
    });

    document.getElementById("reservationDetail").innerHTML =
        "<p>Selecteer een reservering</p>";

    fetchReservations();
}

function filterReservations() {
    const q = document.getElementById("search").value.toLowerCase();
    renderList(
        reservations.filter(r =>
            (`${r.id}`).includes(q)
        )
    );
}

fetchReservations();
