// List of facilities
let facilities = [];
let editIndex = null;

const form = document.getElementById("facilityForm");
const listEl = document.getElementById("facilitiesList");
const publicEl = document.getElementById("publicFacilities");
const toast = document.getElementById("toast");
const resetBtn = document.getElementById("resetBtn");
const storedFacilities = localStorage.getItem("facilities");
if (storedFacilities) {
    facilities = JSON.parse(storedFacilities);
}

function saveFacilities() {
    localStorage.setItem("facilities", JSON.stringify(facilities));
}

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const facility = {
        name: document.getElementById("name").value.trim(),
        description: document.getElementById("description").value.trim(),
        image: document.getElementById("image").value.trim(),
        status: document.getElementById("available").checked ? "available" : "unavailable"
    };

    if (editIndex !== null) {
        facilities[editIndex] = facility;
    } else {
        facilities.push(facility);
    }
    saveFacilities();
    showToast(editIndex !== null ? "Facility updated" : "Facility added");


    editIndex = null;
    form.reset();
    renderFacilities();
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
    facilities.forEach((f, i) => {
        const li = document.createElement("li");
        li.className = "facility-item";

        li.innerHTML = `
      <span>${f.name} (${f.status === "available" ? "Available" : "Not available"})</span>
      <div class="facility-actions">
        <button class="btn-ghost" onclick="editFacility(${i})">Edit</button>
        <button class="btn-ghost" onclick="deleteFacility(${i})">Delete</button>
      </div>
    `;

        listEl.appendChild(li);
    });

    // Public view
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
function editFacility(index) {
    const f = facilities[index];
    document.getElementById("name").value = f.name;
    document.getElementById("description").value = f.description;
    document.getElementById("image").value = f.image;
    document.getElementById("available").checked = f.status === "available";
    editIndex = index;
}

/**
 * Remove a facility from the list and refresh the UI.
 * @param {number} index - The index of the facility to remove.
 */
function deleteFacility(index) {
    if (confirm("Weet je zeker dat je deze faciliteit wilt verwijderen?")) {
        facilities.splice(index, 1);
        saveFacilities();
        showToast("Facility removed");
        renderFacilities();
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

renderFacilities();