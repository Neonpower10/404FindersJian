// API endpoint URLs
const RESERVATIONS_API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/reservations";
const PRODUCTS_API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

let reservations = [];
let products = [];
let selectedReservationId = null;

// Get HTML elements
const reservationsListElement = document.getElementById("reservationsLijst");
const emptyReservationsText = document.getElementById("leegReservationsText");
const searchInput = document.getElementById("searchInput");
const reservationDetailsElement = document.getElementById("reservationDetails");

// Load all products from API
async function loadProducts() {
    try {
        const response = await fetch(PRODUCTS_API_URL);
        if (response.ok) {
            products = await response.json();
        }
    } catch (error) {
        console.error("Error loading products:", error);
    }
}

// Get product name by product ID
function getProductName(productId) {
    for (let i = 0; i < products.length; i++) {
        if (products[i].productId === productId) {
            return products[i].productName;
        }
    }
    return "Onbekend product";
}

// Load all reservations from API
async function loadReservations() {
    try {
        const response = await fetch(RESERVATIONS_API_URL);
        if (!response.ok) {
            alert("Er ging iets mis bij het ophalen van de reserveringen.");
            return;
        }
        reservations = await response.json();
        showReservations();
    } catch (error) {
        alert("Er ging iets mis bij het ophalen van de reserveringen.");
    }
}

// Display reservations in the list
function showReservations() {
    reservationsListElement.innerHTML = "";
    
    const searchTerm = searchInput.value.toLowerCase();
    const filtered = [];
    
    for (let i = 0; i < reservations.length; i++) {
        const customerName = (reservations[i].customerName || "").toLowerCase();
        if (customerName.indexOf(searchTerm) !== -1) {
            filtered.push(reservations[i]);
        }
    }
    
    if (filtered.length === 0) {
        emptyReservationsText.style.display = "block";
    } else {
        emptyReservationsText.style.display = "none";
    }
    
    for (let i = 0; i < filtered.length; i++) {
        const reservation = filtered[i];
        const div = document.createElement("div");
        
        let className = "reservation";
        if (selectedReservationId === reservation.reservationId) {
            className += " selected";
        }
        div.className = className;
        
        const reservationId = reservation.reservationId;
        const idString = reservationId < 10 ? "00" + reservationId : (reservationId < 100 ? "0" + reservationId : reservationId);
        
        div.innerHTML = 
            "<div class='reservation-header'>Reservering #" + idString + "</div>" +
            "<div class='reservation-info'><div>Aantal: " + reservation.productAmount + "</div></div>" +
            "<div class='reservation-buttons'>" +
            "<button onclick='window.editReservation(" + reservationId + ")' class='btn-tonal'>Bewerk</button>" +
            "<button onclick='window.viewReservation(" + reservationId + ")' class='btn-primary'>Bekijk</button>" +
            "<button onclick='window.deleteReservation(" + reservationId + ")' class='btn-danger'>Verwijder</button>" +
            "</div>";
        
        div.onclick = function(event) {
            if (!event.target.closest("button")) {
                window.viewReservation(reservationId);
            }
        };
        
        reservationsListElement.appendChild(div);
    }
}

// Show reservation details in the right panel
window.viewReservation = function(id) {
    let reservation = null;
    for (let i = 0; i < reservations.length; i++) {
        if (reservations[i].reservationId === id) {
            reservation = reservations[i];
            break;
        }
    }
    
    if (!reservation) return;
    
    selectedReservationId = id;
    showReservations();
    
    const startDateObj = new Date(reservation.reservationStartDate + "T00:00:00");
    const endDateObj = new Date(reservation.reservationEndDate + "T00:00:00");
    const startDate = startDateObj.toLocaleDateString('nl-NL');
    const endDate = endDateObj.toLocaleDateString('nl-NL');
    
    const timeDiff = endDateObj - startDateObj;
    const days = Math.ceil(timeDiff / (1000 * 60 * 60 * 24)) + 1;
    const totalPrice = days * parseFloat(reservation.pricePerDay) * reservation.productAmount;
    
    const productName = getProductName(reservation.productId);
    const pricePerDay = parseFloat(reservation.pricePerDay).toFixed(2);
    const totalPriceString = totalPrice.toFixed(2);
    
    reservationDetailsElement.innerHTML =
        "<div class='detail-item'><div class='detail-label'>Aantal producten</div>" +
        "<div class='detail-value'>" + reservation.productAmount + "</div></div>" +
        "<div class='detail-item'><div class='detail-label'>Productnaam</div>" +
        "<div class='detail-value'>" + productName + "</div></div>" +
        "<div class='detail-item'><div class='detail-label'>Huurperiode</div>" +
        "<div class='detail-value'>" + startDate + " tot " + endDate + " (" + days + " dagen)</div></div>" +
        "<div class='detail-item'><div class='detail-label'>Klantnaam</div>" +
        "<div class='detail-value'>" + (reservation.customerName || "Niet opgegeven") + "</div></div>" +
        "<div class='detail-item'><div class='detail-label'>Prijs per dag</div>" +
        "<div class='detail-value'>€" + pricePerDay + "</div></div>" +
        "<div class='detail-item'><div class='detail-label'>Totale prijs</div>" +
        "<div class='detail-value'><strong>€" + totalPriceString + "</strong></div></div>" +
        "<div class='detail-actions'>" +
        "<button onclick='window.editReservation(" + id + ")' class='btn-primary'>Bewerk</button>" +
        "<button onclick='window.deleteReservation(" + id + ")' class='btn-danger'>Verwijder</button></div>";
};

// Edit reservation (placeholder for future implementation)
window.editReservation = function(id) {
    alert("Bewerk functionaliteit komt binnenkort beschikbaar.");
};

// Delete a reservation
window.deleteReservation = async function(id) {
    let idString = id < 10 ? "00" + id : (id < 100 ? "0" + id : id);
    if (!confirm('Weet je zeker dat je reservering #' + idString + ' wilt verwijderen?')) {
        return;
    }
    try {
        const response = await fetch(RESERVATIONS_API_URL + "/" + id, { method: "DELETE" });
        if (!response.ok && response.status !== 204) {
            alert("Er ging iets mis bij het verwijderen van de reservering.");
            return;
        }
        if (selectedReservationId === id) {
            selectedReservationId = null;
            reservationDetailsElement.innerHTML = "<p class='leeg'>Selecteer een reservering om details te bekijken.</p>";
        }
        await loadReservations();
    } catch (error) {
        alert("Er ging iets mis bij het verwijderen van de reservering.");
    }
};

// Search functionality
searchInput.addEventListener("input", function() {
    showReservations();
});

// Load data when page loads
loadProducts().then(function() {
    loadReservations();
});
