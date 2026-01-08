/**
 * bookingsAdmin.js
 *
 * Handles fetching and displaying bookings in the admin bookings table.
 * Uses BookingService to retrieve booking data from the backend API.
 */

import BookingService from "./service/BookingService.js";

/**
 * Reference to the HTML table where bookings will be displayed.
 * @type {HTMLTableElement}
 */
const table = document.getElementById("bookingsTable");

/**
 * Fetch all bookings from the backend and display them in the table.
 * Logs an error to the console if fetching fails.
 */
BookingService.getAllBookings()
    .then(bookings => {
        showBookings(bookings);
    })
    .catch(error => {
        console.error("Failed to load bookings:", error);
    });

/**
 * Populates the bookings table with the given bookings array.
 * Clears existing rows (except the header) before inserting new data.
 *
 * @param {Array<Object>} bookings - Array of booking objects returned from the backend.
 */
function showBookings(bookings) {
    // Remove existing rows except for the header
    const oldRows = table.querySelectorAll("tr:not(:first-child)");
    oldRows.forEach(row => row.remove());

    // Insert each booking as a new row
    bookings.forEach(booking => {
        const row = table.insertRow();
        row.insertCell(0).innerText = booking.bookingId;
        row.insertCell(1).innerText = booking.amountPersons;
        row.insertCell(2).innerText = booking.startDate;
        row.insertCell(3).innerText = booking.endDate;

        // Add click event to redirect to booking details page
        // TODO: Create bookingDetails.html and pass bookingId to display details
        row.addEventListener("click", () => {
            window.location.href = "/bookingDetails.html";
        });
    });
}
