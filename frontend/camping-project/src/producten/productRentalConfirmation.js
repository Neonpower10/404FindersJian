const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/reservations";

const reservationSummary = document.getElementById("reservationSummary");
const totalPriceElement = document.getElementById("totalPrice");

let rentalData = null;

// Load rental data from sessionStorage
function loadRentalData() {
    const data = sessionStorage.getItem("rentalData");
    if (!data) {
        alert("Geen reserveringsgegevens gevonden. Ga terug naar de productenlijst.");
        window.location.href = "/productRentalList.html";
        return;
    }
    
    rentalData = JSON.parse(data);
    displaySummary();
}

// Display reservation summary
function displaySummary() {
    const totalPrice = rentalData.totalDays * parseFloat(rentalData.pricePerDay) * rentalData.quantity;
    
    const startDateObj = new Date(rentalData.startDate + "T00:00:00");
    const endDateObj = new Date(rentalData.endDate + "T00:00:00");
    const startDate = startDateObj.toLocaleDateString('nl-NL');
    const endDate = endDateObj.toLocaleDateString('nl-NL');
    
    reservationSummary.innerHTML =
        "<div class='summary-item'><div class='summary-label'>Aantal producten</div><div class='summary-value'>" + rentalData.quantity + "</div></div>" +
        "<div class='summary-item'><div class='summary-label'>Productnaam</div><div class='summary-value'>" + rentalData.productName + "</div></div>" +
        "<div class='summary-item'><div class='summary-label'>Huurperiode</div><div class='summary-value'>" + startDate + " tot " + endDate + "</div></div>";
    
    totalPriceElement.textContent = totalPrice.toFixed(2);
}

// Submit reservation to API
window.submitReservation = async function() {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    
    // Check if all fields are filled
    if (!firstName || !lastName || !email || !phone) {
        alert("Vul alle velden in.");
        return;
    }
    
    // Validate email format
    if (email.indexOf("@") === -1 || email.indexOf(".") === -1) {
        alert("Vul een geldig e-mailadres in.");
        return;
    }
    
    // Validate phone number (check if it has at least 10 digits)
    let phoneDigits = "";
    for (let i = 0; i < phone.length; i++) {
        if (phone[i] >= "0" && phone[i] <= "9") {
            phoneDigits += phone[i];
        }
    }
    if (phoneDigits.length < 10) {
        alert("Vul een geldig telefoonnummer in (minimaal 10 cijfers).");
        return;
    }
    
    const customerName = firstName + " " + lastName;
    
    const reservation = {
        productId: rentalData.productId,
        reservationStartDate: rentalData.startDate,
        reservationEndDate: rentalData.endDate,
        productAmount: rentalData.quantity,
        customerName: customerName,
        pricePerDay: parseFloat(rentalData.pricePerDay)
    };
    
    // Log the data being sent for debugging
    console.log("Sending reservation data:", reservation);
    
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(reservation)
        });
        
        console.log("Response status:", response.status);
        
        const responseText = await response.text();
        console.log("Response text:", responseText);
        
        if (!response.ok) {
            let errorMessage = "Er ging iets mis bij het maken van de reservering.";
            try {
                const errorData = JSON.parse(responseText);
                if (errorData.error) {
                    errorMessage = errorData.error;
                } else if (errorData.message) {
                    errorMessage = errorData.message;
                }
            } catch (e) {
                // If JSON parsing fails, use the response text or default message
                if (responseText) {
                    errorMessage = responseText;
                }
            }
            alert("Fout: " + errorMessage);
            return;
        }
        
        const createdReservation = JSON.parse(responseText);
        console.log("Reservation created successfully:", createdReservation);
        
        sessionStorage.removeItem("rentalData");
        alert("Reservering succesvol aangemaakt!");
        window.location.href = "/productRentalList.html";
    } catch (error) {
        if (error.message.indexOf("fetch") !== -1 || error.name === "TypeError") {
            alert("Kan geen verbinding maken met de server. Controleer of de backend server draait op http://localhost:8080");
        } else {
            alert("Er ging iets mis: " + error.message);
        }
    }
};

loadRentalData();
