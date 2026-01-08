/**
 * URL of the backend API endpoint for facilities.
 * Used to fetch facility data from the server.
 */
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/facilities";

/**
 * DOM elements for the facilities grid and message display.
 */
const grid = document.getElementById("facilitiesGrid");
const message = document.getElementById("message");

/**
 * Fetches facilities data from the backend API and renders all available facilities
 * on the page for campers. Facilities marked as "unavailable" are filtered out.
 *
 * @async
 * @function loadFacilities
 * @returns {Promise<void>} Updates the DOM with available facilities or an error message.
 */
async function loadFacilities() {
    try {
        // Request all facilities from the API
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error("Error fetching facilities");

        const facilities = await res.json();

        // Filter out unavailable facilities
        const available = facilities.filter(f => f.status === "available");

        // Clear existing content before re-rendering
        grid.innerText = "";
        message.innerText = "";

        // Display message if no facilities are available
        if (available.length === 0) {
            message.innerText = "Op dit moment zijn er geen faciliteiten beschikbaar.";
            return;
        }

        // Create a card for each available facility
        available.forEach(facility => {
            const card = document.createElement("article");
            card.className = "facility-card";

            card.innerHTML = `
                <img src="${facility.image || "https://via.placeholder.com/400x250?text=No+Image"}" alt="${facility.name}">
                <h2>${facility.name}</h2>
                <p>${facility.description}</p>
            `;

            grid.appendChild(card);
        });
    } catch (err) {
        console.error(err);
        message.innerText = "Er is een fout opgetreden tijdens het ophalen van de faciliteiten.";
    }
}

/**
 * Automatically loads facilities when the script is first executed.
 */
loadFacilities();
