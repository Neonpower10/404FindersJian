// Change this URL if your backend path is different
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/breads";

let breads = [];
let editIndex = null;

const listElement = document.getElementById("broodjesLijst");
const emptyText = document.getElementById("leegText");
const form = document.getElementById("broodjeForm");
const nameInput = document.getElementById("name");
const priceInput = document.getElementById("price");
const descriptionInput = document.getElementById("description");
const imageInput = document.getElementById("image");
const previewImage = document.getElementById("preview");
const searchInput = document.getElementById("zoek");
const formTitle = document.getElementById("formTitel");
const submitButton = document.getElementById("submitBtn");

// Get all breads from the server
async function loadBreads() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            alert("Er ging iets mis bij het ophalen van de broodjes.");
            return;
        }
        breads = await response.json();
        showBreads();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het ophalen van de broodjes.");
    }
}

// Show breads in the list
function showBreads() {
    listElement.innerHTML = "";

    const searchText = searchInput.value.toLowerCase();
    const filteredBreads = breads.filter(function (b) {
        const name = (b.naam || "").toLowerCase();
        return name.indexOf(searchText) !== -1;
    });

    if (filteredBreads.length === 0) {
        emptyText.style.display = "block";
    } else {
        emptyText.style.display = "none";
    }

    filteredBreads.forEach(function (bread, i) {
        const div = document.createElement("div");
        div.className = "broodje";

        // find original index of this bread in the main array
        const originalIndex = breads.indexOf(bread);

        div.innerHTML =
            "<img-bread src='" + (bread.foto || "") + "' alt='foto'>" +
            "<div class='info'>" +
            "<b>" + bread.naam + "</b><br>" +
            "€" + Number(bread.prijs).toFixed(2) + "<br>" +
            (bread.beschrijving || "") +
            "</div>" +
            "<div>" +
            "<button onclick='editBread(" + originalIndex + ")' class='btn-tonal'>" +
            "<span class='material-icons'>edit</span>" +
            "</button>" +
            "<button onclick='deleteBread(" + originalIndex + ")' class='btn-danger'>" +
            "<span class='material-icons'>delete</span>" +
            "</button>" +
            "</div>";

        listElement.appendChild(div);
    });
}

// Delete bread by index
async function deleteBread(index) {
    const bread = breads[index];
    if (!bread || !bread.id) {
        alert("Kon het broodje-id niet vinden.");
        return;
    }

    const confirmDelete = confirm('Weet je zeker dat je "' + bread.naam + '" wilt verwijderen?');
    if (!confirmDelete) {
        return;
    }

    try {
        const response = await fetch(API_URL + "/" + encodeURIComponent(bread.id), {
            method: "DELETE"
        });

        if (!response.ok && response.status !== 204) {
            alert("Er ging iets mis bij het verwijderen van het broodje.");
            return;
        }

        await loadBreads();
        resetForm();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het verwijderen van het broodje.");
    }
}

// Fill form for edit
function editBread(index) {
    const bread = breads[index];
    if (!bread) {
        return;
    }

    editIndex = index;
    nameInput.value = bread.naam || "";
    priceInput.value = bread.prijs != null ? bread.prijs : "";
    descriptionInput.value = bread.beschrijving || "";
    previewImage.src = bread.foto || "";

    formTitle.textContent = "Broodje bewerken"; // visible text stays Dutch
    submitButton.textContent = "Opslaan";       // visible text stays Dutch
    submitButton.className = "bewerken";
}

// Handle form submit
form.onsubmit = function (event) {
    event.preventDefault();

    function saveBread(photoData) {
        const priceNumber = parseFloat(priceInput.value);

        const breadData = {
            naam: nameInput.value,
            prijs: priceNumber,
            beschrijving: descriptionInput.value,
            foto: photoData || previewImage.src || ""
        };

        if (editIndex !== null) {
            updateBread(breadData);
        } else {
            createBread(breadData);
        }
    }

    if (imageInput.files && imageInput.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            saveBread(e.target.result);
        };
        reader.readAsDataURL(imageInput.files[0]);
    } else {
        saveBread(null);
    }
};

// Create new bread (POST)
async function createBread(breadData) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(breadData)
        });

        if (!response.ok) {
            alert("Er ging iets mis bij het aanmaken van het broodje.");
            return;
        }

        await loadBreads();
        resetForm();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het aanmaken van het broodje.");
    }
}

// Update bread (PUT)
async function updateBread(breadData) {
    const oldBread = breads[editIndex];
    if (!oldBread || !oldBread.id) {
        alert("Kon het broodje-id niet vinden voor update.");
        return;
    }

    breadData.id = oldBread.id;

    try {
        const response = await fetch(API_URL + "/" + encodeURIComponent(oldBread.id), {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(breadData)
        });

        if (!response.ok) {
            alert("Er ging iets mis bij het updaten van het broodje.");
            return;
        }

        await loadBreads();
        resetForm();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het updaten van het broodje.");
    }
}

// Image preview when file changes
imageInput.onchange = function () {
    const file = imageInput.files[0];
    if (!file) {
        previewImage.src = "";
        return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
        previewImage.src = e.target.result;
    };
    reader.readAsDataURL(file);
};

// Reset form to default state
function resetForm() {
    form.reset();
    previewImage.src = "";
    formTitle.textContent = "Nieuw broodje"; // visible text stays Dutch
    submitButton.textContent = "Toevoegen";  // visible text stays Dutch
    submitButton.className = "toevoegen";
    editIndex = null;
}

// Filter list when user types
searchInput.oninput = function () {
    showBreads();
};

// First load from server
loadBreads();
