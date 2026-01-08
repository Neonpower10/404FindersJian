import BookingService from "./service/BookingService.js";

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const container = document.getElementById("edit");

    if (!id) {
        container.textContent = "Geen boeking ID meegegeven.";
        return;
    }

    BookingService.getBookingById(id)
        .then((b) => {
            container.innerHTML = `
                <button onclick="history.back()" class="back-btn">← Terug</button>
            
                <h2>Boeking #${b.bookingId}</h2>
            
                <div id="displayMode">
                    <p><strong>Naam:</strong> ${b.personFirstName} ${b.personLastName}</p>
                    <p><strong>Email:</strong> ${b.email}</p>
                    <p><strong>Telefoonnummer:</strong> ${b.phoneNumber}</p>
                    <p><strong>Huisadres:</strong> ${b.streetName} ${b.houseNumber} ${b.postcode} ${b.place} ${b.country}</p>
                    <p><strong>Kampeermiddel:</strong> ${b.campEq}</p>
                    <p><strong>Periode:</strong> 
                        ${new Date(b.startDate).toLocaleDateString('nl-NL')} → 
                        ${new Date(b.endDate).toLocaleDateString('nl-NL')}
                    </p>
                    <p><strong>Aangemaakt op:</strong> ${b.creationDate}</p>
                    <p><strong>Aantal personen:</strong> ${b.amountPersons}</p>
            
                    <button id="editBtn">Bewerken</button>
                </div>
            
                <div id="editMode" style="display:none;">
                    <label>Voornaam:<input id="firstNameInput" type="text" value="${b.personFirstName}"></label><br>
                    <label>Achternaam:<input id="lastNameInput" type="text" value="${b.personLastName}"></label><br>
                    <label>Email:<input id="emailInput" type="email" value="${b.email}"></label><br>
                    <label>Telefoonnummer:<input id="phoneInput" type="text" value="${b.phoneNumber}"></label><br>
                    <label>Straatnaam:<input id="streetNameInput" type="text" value="${b.streetName}"></label><br>
                    <label>Huisnummer:<input id="houseNumberInput" type="text" value="${b.houseNumber}"></label><br>
                    <label>Postcode:<input id="postcodeInput" type="text" value="${b.postcode}"></label><br>
                    <label>Plaats:<input id="placeInput" type="text" value="${b.place}"></label><br>
                    <label>Land:<input id="countryInput" type="text" value="${b.country}"></label><br>
                    <label>Kampeermiddel:<input id="campEqInput" type="text" value="${b.campEq}"></label><br>
                    <label>Startdatum:<input id="startDateInput" type="date" value="${b.startDate}"></label><br>
                    <label>Einddatum:<input id="endDateInput" type="date" value="${b.endDate}"></label><br>
                    <label>Aantal personen:<input id="personsInput" type="number" value="${b.amountPersons}"></label><br>
            
                    <button id="saveBtn">Opslaan</button>
                    <button id="cancelBtn">Annuleren</button>
                </div>
            `;

            document.getElementById("editBtn").addEventListener("click", () => {
                document.getElementById("displayMode").style.display = "none";
                document.getElementById("editMode").style.display = "block";
            });

            document.getElementById("cancelBtn").addEventListener("click", () => {
                document.getElementById("editMode").style.display = "none";
                document.getElementById("displayMode").style.display = "block";
            });

            document.getElementById("saveBtn").addEventListener("click", async () => {
                const updatedBooking = {
                    personFirstName: document.getElementById("firstNameInput").value,
                    personLastName: document.getElementById("lastNameInput").value,
                    email: document.getElementById("emailInput").value,
                    phoneNumber: document.getElementById("phoneInput").value,
                    streetName: document.getElementById("streetNameInput").value,
                    houseNumber: document.getElementById("houseNumberInput").value,
                    postcode: document.getElementById("postcodeInput").value,
                    place: document.getElementById("placeInput").value,
                    country: document.getElementById("countryInput").value,
                    campEq: document.getElementById("campEqInput").value,
                    startDate: document.getElementById("startDateInput").value,
                    endDate: document.getElementById("endDateInput").value,
                    amountPersons: parseInt(document.getElementById("personsInput").value)
                };

                try {
                    await BookingService.updateBooking(id, updatedBooking);
                    alert("Boeking bijgewerkt!");
                    window.location.reload();
                } catch (err) {
                    console.error(err);
                    alert("Fout bij bijwerken van de boeking.");
                }
            });


        })
        .catch((err) => {
            console.error(err);
            container.textContent = "Boeking niet gevonden.";
        });
});
