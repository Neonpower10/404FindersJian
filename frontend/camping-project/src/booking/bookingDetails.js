import BookingService from "./service/BookingService.js";

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const container = document.getElementById("details");

    if (!id) {
        container.textContent = "Geen boeking ID meegegeven.";
        return;
    }

    BookingService.getBookingById(id)
        .then((b) => {
            container.innerHTML = `
                <button onclick="history.back()" class="back-btn">← Terug</button>
                
                <h2>Boeking #${b.bookingId}</h2>

                <p><strong>Naam:</strong> ${b.personFirstName} ${b.personLastName}</p>
                <p><strong>Email:</strong> ${b.email}</p>
                <p><strong>Telefoonnummer:</strong> ${b.phoneNumber}</p>
                <p><strong>Huisadres:</strong> ${b.streetName} ${b.houseNumber} ${b.postcode} ${b.place} ${b.country}</p>
                <p><strong>Kampeermiddel:</strong> ${b.campPlace}</p>
                <p><strong>Periode:</strong> 
                    ${new Date(b.startDate).toLocaleDateString('nl-NL')} → 
                    ${new Date(b.endDate).toLocaleDateString('nl-NL')}
                </p>
                <p><strong>Aantal personen:</strong> ${b.amountPersons}</p>
            `;
        })
        .catch((err) => {
            console.error(err);
            container.textContent = "Boeking niet gevonden.";
        });
});
