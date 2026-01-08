import BookingService from "./service/BookingService.js";

let bookings = [];

window.addEventListener("DOMContentLoaded", () => {
    const listContainer = document.getElementById("bookings-list");
    const searchInput = document.getElementById("search");

    BookingService.getAllBookings()
        .then((data) => {
            bookings = data;
            renderBookings(bookings);
        })
        .catch((err) => {
            console.error("Failed to load bookings:", err);
        });

    function renderBookings(list) {
        listContainer.innerHTML = "";

        list.forEach((b) => {
            const card = document.createElement("div");
            card.className = "booking-card";


            card.innerHTML = `
                <h3>Boeking #${b.bookingId} – ${b.personFirstName} ${b.personLastName}</h3>
                <p><strong>Periode:</strong> 
                    ${new Date(b.startDate).toLocaleDateString('nl-NL')} → 
                    ${new Date(b.endDate).toLocaleDateString('nl-NL')}
                </p>
                <p><strong>Personen:</strong> ${b.amountPersons}</p>
                <div class="actions">
                    <button class="deleteBooking" data-id="${b.bookingId}">
                        Boeking annuleren
                    </button>
                    <button class="editBooking" data-id="${b.bookingId}">
                        Bekijk & Bewerk
                    </button>
                </div>
            `;

            listContainer.appendChild(card);
        });
    }

    listContainer.addEventListener("click", async (event) => {
        const btn = event.target.closest("button");
        if (!btn) return;

        const id = btn.dataset.id;

        if (btn.classList.contains("deleteBooking")) {
            const confirmDelete = confirm("Weet je zeker dat je deze booking wilt verwijderen?");
            if (!confirmDelete) return;

            try {
                await BookingService.deleteBooking(id);

                // remove from UI memory
                bookings = bookings.filter((b) => b.bookingId != id);

                // re-render
                renderBookings(bookings);
            } catch (err) {
                console.error("Delete failed:", err);
                alert("Kan booking niet verwijderen.");
            }
        }

        if (btn.classList.contains("editBooking")) {
            window.location.href = `/bookingEdit.html?id=${id}`;
        }
    });

    searchInput.addEventListener("input", () => {
        const term = searchInput.value.toLowerCase();
        const filtered = bookings.filter((b) =>
            `${b.personFirstName} ${b.personLastName}`.toLowerCase().includes(term)
        );
        renderBookings(filtered);
    });
});
