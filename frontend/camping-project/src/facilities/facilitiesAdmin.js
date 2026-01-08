// List of facilities
let facilities = [];
let editId = null;
let currentImageBase64 = null;

const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/facilities";

const form = document.getElementById("facilityForm");
const listEl = document.getElementById("facilitiesList");
const publicEl = document.getElementById("publicFacilities");
const toast = document.getElementById("toast");
const resetBtn = document.getElementById("resetBtn");
const imageInput = document.getElementById("image");

// Convert selected file to Base64
function fileToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}

imageInput.addEventListener("change", async () => {
    const file = imageInput.files[0];
    if (file) {
        currentImageBase64 = await fileToBase64(file);
    }
});

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const description = document.getElementById("description").value.trim();
    const status = document.getElementById("available").checked ? "available" : "unavailable";

    // If adding a new facility and no image selected → block submission
    if (editId === null && !currentImageBase64) {
        showToast("Upload een afbeelding voor deze faciliteit.");
        return;
    }

    // If editing and no new image selected, keep old image
    let imageToSend = currentImageBase64;
    if (editId !== null && !imageInput.files[0]) {
        const original = facilities.find(f => f.id === editId);
        imageToSend = original.image;
    }

    const facility = { name, description, status, image: imageToSend };

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
            showToast("Faciliteit bijgewerkt!");
        } else {
            await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
                },
                body: JSON.stringify(facility)
            });
            showToast("Faciliteit toegevoegd!");
        }

        form.reset();
        currentImageBase64 = null;
        editId = null;
        fetchFacilities();
    } catch (err) {
        console.error("Fout bij opslaan van faciliteit:", err);
    }
});

resetBtn.addEventListener("click", () => {
    form.reset();
    currentImageBase64 = null;
    editId = null;
});

/**
 * Render the facilities list for both the admin view and the public view.
 * - Admin view shows all facilities with options to edit or delete.
 * - Public view shows only available facilities with image, name, and description.
 */
function renderFacilities() {
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
    const availableFacilities = facilities.filter(f => f.status === "available");
// NEW: Show message when no facilities are available
    if (availableFacilities.length === 0) {
        const msg = document.createElement("p");
        msg.className = "empty-message";
        msg.innerText = "(Nog) Geen faciliteiten beschikbaar of aanwezig!";
        publicEl.appendChild(msg);
        return;
    }

    availableFacilities.forEach(f => {
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
window.editFacility = function(id) {
    const f = facilities.find(f => f.id === id);
    document.getElementById("name").value = f.name;
    document.getElementById("description").value = f.description;
    document.getElementById("available").checked = f.status === "available";

    currentImageBase64 = f.image;
    imageInput.value = "";

    editId = id;
};

/**
 * Remove a facility from the list and refresh the UI.
 * @param {number} index - The index of the facility to remove.
 */
window.deleteFacility = async function(id) {
    if (confirm("Weet je zeker dat je deze faciliteit wilt verwijderen?")) {
        await fetch(`${API_URL}/${id}`, {method: "DELETE",
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
            }
        });
        showToast("Faciliteit verwijderd!");
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
