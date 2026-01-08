// campingDataAdmin.js
// Handles loading, displaying, and saving camping data through the API

const apiUrl = "http://localhost:8080/api/camping";

document.addEventListener("DOMContentLoaded", () => {
    loadCampingData();

    const form = document.getElementById("camping-data-form");
    if (form) {
        form.addEventListener("submit", async (event) => {
            event.preventDefault();
            saveCampingData();
        });
    }
});

/**
 * Loads camping data from the backend API and displays it
 */
async function loadCampingData() {
    const container = document.getElementById("camping-data-list");
    if (!container) return;

    container.innerHTML = "<p>Loading...</p>";

    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Failed to load camping data");

        const data = await response.json();

        if (data.length === 0) {
            container.innerHTML = "<p>No camping data found.</p>";
            return;
        }

        container.innerHTML = data.map(entry => `
            <div class="camping-entry">
                <h3>${entry.name}</h3>
                <p><strong>Description:</strong> ${entry.description}</p>
                <p><strong>Region:</strong> ${entry.region}</p>
                <p><strong>Price:</strong> €${entry.price}</p>
                <hr>
            </div>
        `).join("");

    } catch (err) {
        container.innerHTML = "<p>Error loading data.</p>";
        console.error(err);
    }
}

/**
 * Saves a new campingData entry using POST
 */
async function saveCampingData() {
    const name = document.getElementById("camping-name").value;
    const description = document.getElementById("camping-description").value;
    const region = document.getElementById("camping-region").value;
    const price = document.getElementById("camping-price").value;

    const newCampingData = {
        name,
        description,
        region,
        price: parseFloat(price)
    };

    try {
        const response = await fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newCampingData)
        });

        if (response.ok) {
            alert("Camping data saved!");
            loadCampingData();
            document.getElementById("camping-data-form").reset();
        } else {
            throw new Error("Failed to save camping data");
        }

    } catch (err) {
        alert("Error saving camping data");
        console.error(err);
    }
}
