// Admin-only guard
const role = sessionStorage.getItem("role");
if (role !== "admin") {
    window.location.href = "/index.html";
}

// List of facilities
let facilities = [];
let editId = null;

const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/facilities";


const form = document.getElementById("facilityForm");
const listEl = document.getElementById("facilitiesList");
const publicEl = document.getElementById("publicFacilities");
const toast = document.getElementById("toast");
const resetBtn = document.getElementById("resetBtn");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const facility = {
        name: document.getElementById("name").value.trim(),
        description: document.getElementById("description").value.trim(),
        image: document.getElementById("image").value.trim(),
        status: document.getElementById("available").checked ? "available" : "unavailable"
    };

    try {
        if (editId !== null) {
            await fetch(`${API_URL}/${editId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
                },
                body: JSON.stringify(facility)
            });
            showToast("Facility updated");
        } else {
            await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
                },
                body: JSON.stringify(facility)
            });
            showToast("Facility added");
        }

        form.reset();
        editId = null;
        fetchFacilities();
    } catch (err) {
        console.error("Error saving facility:", err);
    }
});

resetBtn.addEventListener("click", () => {
    form.reset();
    editIndex = null;
});

/**
 * Render the facilities list for both the admin view and the public view.
 * - Admin view shows all facilities with options to edit or delete.
 * - Public view shows only available facilities with image, name, and description.
 */
function renderFacilities() {
    // Admin list
    listEl.innerHTML = "";
    facilities.forEach(f => {
        const li = document.createElement("li");
        li.className = "facility-item";


        li.innerHTML = `
            <span>${f.name} (${f.status === "available" ? "Available" : "Not available"})</span>
            <div class="facility-actions">
                <button class="btn-ghost" onclick="editFacility(${f.id})">Edit</button>
                <button class="btn-ghost" onclick="deleteFacility(${f.id})">Delete</button>
            </div>
        `;
        listEl.appendChild(li);
    });

    // Public list
    publicEl.innerHTML = "";
    facilities
        .filter(f => f.status === "available")
        .forEach(f => {
            const div = document.createElement("div");
            div.className = "facility-card";
            div.innerHTML = `
                ${f.image ? `<img src="${f.image}" alt="${f.name}">` : ""}
                <h3>${f.name}</h3>
                <p>${f.description}</p>
            `;
            publicEl.appendChild(div);
        });
}


/**
 * Load facility data into the form for editing.
 * @param {number} index - The index of the facility in the facilities array.
 */
function editFacility(id) {
    const f = facilities.find(f => f.id === id);
    document.getElementById("name").value = f.name;
    document.getElementById("description").value = f.description;
    document.getElementById("image").value = f.image;
    document.getElementById("available").checked = f.status === "available";
    editId = id;
}


/**
 * Remove a facility from the list and refresh the UI.
 * @param {number} index - The index of the facility to remove.
 */
async function deleteFacility(id) {
    if (confirm("Weet je zeker dat je deze faciliteit wilt verwijderen?")) {
        await fetch(`${API_URL}/${id}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
            }
        });
        showToast("Facility removed");
        fetchFacilities();
    }
}



/**
 * Show a temporary notification message.
 * @param {string} msg - The message to display in the toast.
 */
function showToast(msg) {
    toast.innerText = msg;
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), 2000);
}

async function fetchFacilities() {
    try {
        const res = await fetch(API_URL);
        facilities = await res.json();
        renderFacilities();
    } catch (err) {
        console.error("Error fetching facilities:", err);
    }
}

fetchFacilities();
