// API endpoint URL - only get available products
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products/available";

const productsListElement = document.getElementById("productsList");
const loadingText = document.getElementById("loadingText");
const errorText = document.getElementById("errorText");
const scrollLeftBtn = document.getElementById("scrollLeft");
const scrollRightBtn = document.getElementById("scrollRight");
const productsScroll = document.getElementById("productsScroll");

// Load all available products from API
async function loadProducts() {
    try {
        loadingText.style.display = "block";
        errorText.style.display = "none";
        
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error("Failed to load products");
        }
        
        const products = await response.json();
        displayProducts(products);
        loadingText.style.display = "none";
    } catch (error) {
        loadingText.style.display = "none";
        errorText.style.display = "block";
    }
}

// Display products in the list
function displayProducts(products) {
    productsListElement.innerHTML = "";
    
    if (products.length === 0) {
        productsListElement.innerHTML = "<p>Geen producten beschikbaar.</p>";
        return;
    }
    
    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        const div = document.createElement("div");
        
        let className = "product-card";
        if (product.productStock === 0) {
            className += " not-available";
        }
        div.className = className;
        
        let imageUrl = product.productImageUrl;
        if (!imageUrl) {
            imageUrl = '/public/icons/campingMoodImage.jpg';
        }
        
        let priceHtml = "";
        if (product.productStock === 0) {
            priceHtml = "<p class='not-available-text'>Niet beschikbaar</p>";
        } else {
            const price = parseFloat(product.pricePerDay).toFixed(2);
            priceHtml = "<p class='price'>€" + price + " per dag</p>";
        }
        
        let buttonDisabled = "";
        if (product.productStock === 0) {
            buttonDisabled = "disabled";
        }
        
        div.innerHTML =
            "<img src='" + imageUrl + "' alt='" + product.productName + "'>" +
            "<h3>" + product.productName + "</h3>" +
            priceHtml +
            "<button onclick='window.rentProduct(" + product.productId + ")' " + buttonDisabled + ">Lees meer</button>";
        
        productsListElement.appendChild(div);
    }
}

// Navigate to product detail page
window.rentProduct = function(productId) {
    window.location.href = "/productRentalDetail.html?id=" + productId;
}

// Scroll buttons
scrollLeftBtn.addEventListener("click", function() {
    productsScroll.scrollBy({ left: -300, behavior: 'smooth' });
});

scrollRightBtn.addEventListener("click", function() {
    productsScroll.scrollBy({ left: 300, behavior: 'smooth' });
});

// Load products when page loads
loadProducts();
