// API endpoint URL
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

let products = [];
let editProductId = null;

// Get HTML elements - two separate lists: one for concept (unpublished) products, one for published products
const conceptListElement = document.getElementById("conceptLijst");
const gepubliceerdListElement = document.getElementById("gepubliceerdLijst");
const emptyConceptText = document.getElementById("leegConceptText");
const emptyGepubliceerdText = document.getElementById("leegGepubliceerdText");
const form = document.getElementById("productForm");
const productNameInput = document.getElementById("productName");
const productDescriptionInput = document.getElementById("productDescription");
const productPriceInput = document.getElementById("productPrice");
const productStockInput = document.getElementById("productStock");
const productImageInput = document.getElementById("productImage");
const previewImage = document.getElementById("preview");
const chooseFileBtn = document.getElementById("chooseFileBtn");
const formTitle = document.getElementById("formTitel");
const submitButton = document.getElementById("submitBtn");
const resetBtn = document.getElementById("resetBtn");

// File input button (if it exists - optional)
if (chooseFileBtn) {
    chooseFileBtn.addEventListener("click", function() {
        productImageInput.click();
    });
}

// Show image preview when file is selected
productImageInput.addEventListener("change", function() {
    const file = productImageInput.files[0];
    if (!file) return;
    
    const reader = new FileReader();
    reader.onload = function(e) {
        previewImage.src = e.target.result;
    };
    reader.readAsDataURL(file);
});

// Load all products from API
async function loadProducts() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            alert("Er ging iets mis bij het ophalen van de producten.");
            return;
        }
        products = await response.json();
        showProducts();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het ophalen van de producten.");
    }
}

// Show products in two separate lists: concept (unpublished) and published
function showProducts() {
    // Clear both lists
    conceptListElement.innerHTML = "";
    gepubliceerdListElement.innerHTML = "";
    
    // Separate products into concept (unpublished) and published
    const conceptProducts = products.filter(function(p) {
        // Product is concept if published is false or not set
        return !p.published;
    });
    
    const gepubliceerdProducts = products.filter(function(p) {
        // Product is published if published is true
        return p.published === true;
    });
    
    // Show/hide empty messages for concept list
    if (conceptProducts.length === 0) {
        emptyConceptText.style.display = "block";
    } else {
        emptyConceptText.style.display = "none";
    }
    
    // Show/hide empty messages for published list
    if (gepubliceerdProducts.length === 0) {
        emptyGepubliceerdText.style.display = "block";
    } else {
        emptyGepubliceerdText.style.display = "none";
    }
    
    // Display concept products (unpublished)
    conceptProducts.forEach(function (product) {
        const div = createProductCard(product, false); // false = not published, show publish button
        conceptListElement.appendChild(div);
    });
    
    // Display published products
    gepubliceerdProducts.forEach(function (product) {
        const div = createProductCard(product, true); // true = published, show unpublish button
        gepubliceerdListElement.appendChild(div);
    });
}

// Helper function to create a product card (used for both concept and published lists)
function createProductCard(product, isPublished) {
    const div = document.createElement("div");
    div.className = "product";
    
    const productId = product.productId;
    
    let imageUrl = product.productImageUrl;
    if (!imageUrl) {
        imageUrl = '/public/icons/campingMoodImage.jpg';
    }
    
    // Create buttons - concept products get a "Publish" button, published products get an "Unpublish" button
    let publishButton = "";
    if (!isPublished) {
        // Show publish button for concept products
        publishButton = "<button onclick='publishProduct(" + productId + ")' class='btn-primary'>" +
            "<span class='material-symbols-outlined'>publish</span> Publiceer" +
            "</button>";
    } else {
        // Show unpublish button for published products
        publishButton = "<button onclick='unpublishProduct(" + productId + ")' class='btn-primary btn-warning'>" +
            "<span class='material-symbols-outlined'>visibility_off</span> Depubliceer" +
            "</button>";
    }
    
    div.innerHTML =
        "<div class='product-image'>" +
        "<img src='" + imageUrl + "' alt='" + product.productName + "'>" +
        "</div>" +
        "<div class='product-content'>" +
        "<div class='product-info'>" +
        "<b>" + product.productName + "</b>" +
        "<div>Aantal beschikbaar: " + product.productStock + "</div>" +
        "</div>" +
        "<div class='product-buttons'>" +
        publishButton +
        "<button onclick='window.editProduct(" + productId + ")' class='btn-tonal'>" +
        "<span class='material-symbols-outlined'>edit</span> Bewerk" +
        "</button>" +
        "<button onclick='window.deleteProduct(" + productId + ")' class='btn-danger'>" +
        "<span class='material-symbols-outlined'>delete</span> Verwijder" +
        "</button>" +
        "</div>" +
        "</div>";
    
    return div;
}

// Delete a product
window.deleteProduct = async function(id) {
    const product = products.find(p => p.productId === id);
    if (!product) {
        alert("Product niet gevonden.");
        return;
    }
    
    if (!confirm('Weet je zeker dat je "' + product.productName + '" wilt verwijderen?')) {
        return;
    }
    
    try {
        const response = await fetch(API_URL + "/" + id, { method: "DELETE" });
        if (!response.ok && response.status !== 204) {
            alert("Er ging iets mis bij het verwijderen van het product.");
            return;
        }
        await loadProducts();
        resetForm();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het verwijderen van het product.");
    }
}

// Fill form with product data for editing
window.editProduct = function(id) {
    const product = products.find(p => p.productId === id);
    if (!product) {
        alert("Product niet gevonden.");
        return;
    }
    
    editProductId = id;
    productNameInput.value = product.productName || "";
    productDescriptionInput.value = product.productDescription || "";
    productPriceInput.value = product.pricePerDay || "";
    productStockInput.value = product.productStock || "";
    previewImage.src = product.productImageUrl || "";
    formTitle.textContent = "Product bewerken";
    submitButton.textContent = "Wijzigingen opslaan";
    resetBtn.style.display = "block";
}

// Handle form submit
form.addEventListener("submit", async function(event) {
    event.preventDefault();
    
    const productData = {
        productName: productNameInput.value,
        productDescription: productDescriptionInput.value,
        pricePerDay: parseFloat(productPriceInput.value),
        productStock: parseInt(productStockInput.value) || 0,
        productImageUrl: previewImage.src || ""
    };
    
    // If new image file is selected, convert to base64
    if (productImageInput.files && productImageInput.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            productData.productImageUrl = e.target.result;
            saveProduct(productData);
        };
        reader.readAsDataURL(productImageInput.files[0]);
    } else {
        saveProduct(productData);
    }
});

// Save product (create new or update existing)
async function saveProduct(productData) {
    try {
        let url = API_URL;
        let method = "POST";
        
        if (editProductId) {
            url = API_URL + "/" + editProductId;
            method = "PUT";
            // Keep the published status when updating (don't change it unless explicitly publishing)
            const oldProduct = products.find(p => p.productId === editProductId);
            if (oldProduct) {
                productData.published = oldProduct.published || false;
            }
        } else {
            // New products start as unpublished (concept)
            productData.published = false;
        }
        
        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(productData)
        });
        
        if (!response.ok) {
            alert("Er ging iets mis bij het opslaan van het product.");
            return;
        }
        
        await loadProducts();
        resetForm();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het opslaan van het product.");
    }
}

// Reset form to default state
function resetForm() {
    form.reset();
    previewImage.src = "";
    formTitle.textContent = "Nieuw product";
    submitButton.textContent = "Product toevoegen";
    resetBtn.style.display = "none";
    editProductId = null;
}

resetBtn.addEventListener("click", resetForm);

// Publish a product (make it visible to customers)
window.publishProduct = async function(id) {
    const product = products.find(p => p.productId === id);
    if (!product) {
        alert("Product niet gevonden.");
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

        // Reload products to update the lists
        await loadProducts();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het publiceren van het product.");
    }
};

// Unpublish a product (make it invisible to customers, move back to concept)
window.unpublishProduct = async function(id) {
    const product = products.find(p => p.productId === id);
    if (!product) {
        alert("Product niet gevonden.");
        return;
    }

    // Confirm before unpublishing
    const confirmUnpublish = confirm('Weet je zeker dat je "' + product.productName + '" wilt unpublishen? Het product zal niet meer zichtbaar zijn voor klanten.');
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

        // Reload products to update the lists
        await loadProducts();
    } catch (error) {
        console.error(error);
        alert("Er ging iets mis bij het unpublishen van het product.");
    }
};

loadProducts();
