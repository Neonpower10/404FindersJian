// Change this URL if your backend path is different
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/breads";

let breads = [];
let editIndex = null;

// Two separate lists: one for concept (unpublished) breads, one for published breads
const conceptListElement = document.getElementById("conceptLijst");
const gepubliceerdListElement = document.getElementById("gepubliceerdLijst");
const emptyConceptText = document.getElementById("leegConceptText");
const emptyGepubliceerdText = document.getElementById("leegGepubliceerdText");
const form = document.getElementById("broodjeForm");
const nameInput = document.getElementById("name");
const priceInput = document.getElementById("price");
const voorraadInput = document.getElementById("voorraad"); // Reference to stock input field
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

// Show breads in two separate lists: concept (unpublished) and published
function showBreads() {
    // Clear both lists
    conceptListElement.innerHTML = "";
    gepubliceerdListElement.innerHTML = "";

    const searchText = searchInput.value.toLowerCase();
    
    // Filter breads based on search text
    const filteredBreads = breads.filter(function (b) {
        const name = (b.name || "").toLowerCase();
        return name.indexOf(searchText) !== -1;
    });

    // Separate breads into concept (unpublished) and published
    const conceptBreads = filteredBreads.filter(function(b) {
        // Bread is concept if published is false or not set
        return !b.published;
    });
    
    const gepubliceerdBreads = filteredBreads.filter(function(b) {
        // Bread is published if published is true
        return b.published === true;
    });

    // Show/hide empty messages for concept list
    if (conceptBreads.length === 0) {
        emptyConceptText.style.display = "block";
    } else {
        emptyConceptText.style.display = "none";
    }

    // Show/hide empty messages for published list
    if (gepubliceerdBreads.length === 0) {
        emptyGepubliceerdText.style.display = "block";
    } else {
        emptyGepubliceerdText.style.display = "none";
    }

    // Display concept breads (unpublished)
    conceptBreads.forEach(function (bread) {
        const div = createBreadCard(bread, false); // false = not published, show publish button
        conceptListElement.appendChild(div);
    });

    // Display published breads
    gepubliceerdBreads.forEach(function (bread) {
        const div = createBreadCard(bread, true); // true = published, don't show publish button
        gepubliceerdListElement.appendChild(div);
    });
}

// Helper function to create a bread card (used for both concept and published lists)
function createBreadCard(bread, isPublished) {
    const div = document.createElement("div");
    div.className = "broodje";

    const breadId = bread.id;
    
    // Get stock value - show message if not set
    let voorraadText = "";
    if (bread.stock != null && bread.stock !== undefined) {
        // Stock is set, show the number
        voorraadText = "Voorraad: " + bread.stock;
    } else {
        // Stock is not set, show warning message
        voorraadText = "<span class='warning-text'>⚠️ Voorraad niet ingesteld</span>";
    }

    // Create buttons - concept breads get a "Publish" button, published breads get an "Unpublish" button
    let publishButton = "";
    if (!isPublished) {
        // Show publish button for concept breads
        publishButton = "<button onclick='publishBread(" + breadId + ")' class='btn-primary'>" +
            "<span class='material-symbols-outlined'>publish</span> Publiceer" +
            "</button>";
    } else {
        // Show unpublish button for published breads
        // Use "visibility_off" icon (eye with slash) to indicate hiding/unpublishing
        publishButton = "<button onclick='unpublishBread(" + breadId + ")' class='btn-primary btn-warning'>" +
            "<span class='material-symbols-outlined'>visibility_off</span> Depubliceer" +
            "</button>";
    }

    // New clean structure: image, content area (with info and buttons side by side)
    // Use regular img tag instead of img-bread custom element
    const imageSrc = bread.photo || '/public/icons/campingMoodImage.jpg';
    div.innerHTML =
        "<div class='broodje-image'>" +
        "<img src='" + imageSrc + "' alt='" + (bread.name || 'Broodje') + "'>" +
        "</div>" +
        "<div class='broodje-content'>" +
        "<div class='broodje-info'>" +
        "<b>" + bread.name + "</b><br>" +
        "€" + Number(bread.price).toFixed(2) + "<br>" +
        voorraadText + "<br>" +
        (bread.description || "") +
        "</div>" +
        "<div class='broodje-buttons'>" +
        publishButton +
        "<button onclick='editBread(" + breadId + ")' class='btn-tonal'>" +
        "<span class='material-symbols-outlined'>edit</span>" +
        "</button>" +
        "<button onclick='deleteBread(" + breadId + ")' class='btn-danger'>" +
        "<span class='material-symbols-outlined'>delete</span>" +
        "</button>" +
        "</div>" +
        "</div>";

    return div;
}

// Delete bread by ID
window.deleteBread = async function(id) {
    const bread = breads.find(b => b.id === id);
    if (!bread) {
        alert("Broodje niet gevonden.");
        return;
    }

    const confirmDelete = confirm('Weet je zeker dat je "' + bread.name + '" wilt verwijderen?');
    if (!confirmDelete) {
        return;
    }

    try {
        const response = await fetch(API_URL + "/" + encodeURIComponent(id), {
            method: "DELETE"
        });

        if (!response.ok && response.status !== 204) {
            if (response.status === 404) {
                alert("Broodje niet gevonden op de server.");
            } else {
                alert("Er ging iets mis bij het verwijderen van het broodje.");
            }
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
window.editBread = function(id) {
    const bread = breads.find(b => b.id === id);
    if (!bread) {
        alert("Broodje niet gevonden.");
        return;
    }

    // Find the index for editIndex (needed for updateBread)
    editIndex = breads.findIndex(b => b.id === id);
    
    nameInput.value = bread.name || "";
    priceInput.value = bread.price != null ? bread.price : "";
    // Set stock value - leave empty if not set (null/undefined), otherwise show the value
    // This makes it clear that stock needs to be set if it's empty
    if (bread.stock != null && bread.stock !== undefined) {
        voorraadInput.value = bread.stock;
    } else {
        voorraadInput.value = ""; // Leave empty to indicate stock needs to be set
    }
    descriptionInput.value = bread.description || "";
    previewImage.src = bread.photo || "";

    formTitle.textContent = "Broodje bewerken"; // visible text stays Dutch
    submitButton.textContent = "Opslaan";       // visible text stays Dutch
    submitButton.className = "bewerken";
}

// Handle form submit
form.onsubmit = function (event) {
    event.preventDefault();

    function saveBread(photoData) {
        const priceNumber = parseFloat(priceInput.value);
        // Get stock value and convert to integer (parseInt converts string to number)
        const voorraadNumber = parseInt(voorraadInput.value) || 0;

        const breadData = {
            name: nameInput.value,
            price: priceNumber,
            stock: voorraadNumber, // Include stock in the data sent to backend
            description: descriptionInput.value,
            photo: photoData || previewImage.src || "",
            published: false // New breads start as unpublished (concept)
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
    if (editIndex === null || editIndex < 0 || editIndex >= breads.length) {
        alert("Kon het broodje niet vinden voor update.");
        return;
    }
    
    const oldBread = breads[editIndex];
    if (!oldBread) {
        alert("Kon het broodje niet vinden voor update.");
        return;
    }
    if (!oldBread.id) {
        alert("Kon het broodje-id niet vinden voor update.");
        return;
    }

    breadData.id = oldBread.id;
    // Keep the published status when updating (don't change it unless explicitly publishing)
    breadData.published = oldBread.published || false;

    try {
        const response = await fetch(API_URL + "/" + encodeURIComponent(oldBread.id), {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(breadData)
        });

        if (!response.ok) {
            if (response.status === 404) {
                alert("Broodje niet gevonden op de server.");
            } else {
                alert("Er ging iets mis bij het updaten van het broodje.");
            }
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

// Publish a bread (make it visible to customers)
window.publishBread = async function(id) {
    const bread = breads.find(b => b.id === id);
    if (!bread) {
        alert("Broodje niet gevonden.");
        return;
    }

    try {
        // Call the publish endpoint
        const response = await fetch(API_URL + "/" + encodeURIComponent(id) + "/publish", {
            method: "PUT"
        });

        if (!response.ok) {
            const result = await response.json();
            alert("Er ging iets mis bij het publiceren: " + (result.message || "Onbekende fout"));
            return;
        }

        // Reload breads to update the lists
        await loadBreads();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het publiceren van het broodje.");
    }
}

// Unpublish a bread (make it invisible to customers, move back to concept)
window.unpublishBread = async function(id) {
    const bread = breads.find(b => b.id === id);
    if (!bread) {
        alert("Broodje niet gevonden.");
        return;
    }

    // Confirm before unpublishing
    const confirmUnpublish = confirm('Weet je zeker dat je "' + bread.name + '" wilt unpublishen? Het broodje zal niet meer zichtbaar zijn voor klanten.');
    if (!confirmUnpublish) {
        return;
    }

    try {
        // Call the unpublish endpoint
        const response = await fetch(API_URL + "/" + encodeURIComponent(id) + "/unpublish", {
            method: "PUT"
        });

        if (!response.ok) {
            const result = await response.json();
            alert("Er ging iets mis bij het unpublishen: " + (result.message || "Onbekende fout"));
            return;
        }

        // Reload breads to update the lists
        await loadBreads();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het unpublishen van het broodje.");
    }
}

// First load from server
loadBreads();
