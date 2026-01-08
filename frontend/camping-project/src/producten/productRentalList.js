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
    loadingText.style.display = "block";
    errorText.style.display = "none";

    try {
        const response = await fetch(API_URL, { cache: "no-store" });
        if (!response.ok) {
            throw new Error(`API responded with status ${response.status}`);
        }

        const products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error("❌ Fout bij het laden van producten:", error);
        errorText.style.display = "block";
    } finally {
        loadingText.style.display = "none";
    }
}

// Display products in the list
function displayProducts(products) {
    productsListElement.innerHTML = "";

    if (!Array.isArray(products) || products.length === 0) {
        productsListElement.innerHTML = "<p>Geen producten beschikbaar.</p>";
        return;
    }

    const fragment = document.createDocumentFragment();

    products.forEach(product => {
        const div = document.createElement("div");
        div.className = "product-card" + (product.productStock === 0 ? " not-available" : "");

        const imageUrl = product.productImageUrl || "/public/icons/campingMoodImage.jpg";
        const price = parseFloat(product.pricePerDay).toFixed(2);
        const isAvailable = product.productStock > 0;

        div.innerHTML = `
            <img src="${imageUrl}" alt="${product.productName}">
            <h3>${product.productName}</h3>
            ${isAvailable
            ? `<p class="price">€${price} per dag</p>`
            : `<p class="not-available-text">Niet beschikbaar</p>`}
            <button 
                ${isAvailable ? "" : "disabled"} 
                onclick="window.rentProduct(${product.productId})"
            >
                Lees meer
            </button>
        `;

        fragment.appendChild(div);
    });

    productsListElement.appendChild(fragment);

    // Force scrollbar to show if needed
    productsScroll.scrollLeft = 0;
}

// Navigate to product detail page
window.rentProduct = function (productId) {
    window.location.href = `/productRentalDetail.html?id=${productId}`;
};

// Scroll buttons
scrollLeftBtn.addEventListener("click", () => {
    productsScroll.scrollBy({ left: -300, behavior: "smooth" });
});

scrollRightBtn.addEventListener("click", () => {
    productsScroll.scrollBy({ left: 300, behavior: "smooth" });
});

// Show/hide scroll buttons depending on scroll position
function updateScrollButtons() {
    const scrollLeft = productsScroll.scrollLeft;
    const maxScrollLeft = Math.ceil(productsScroll.scrollWidth - productsScroll.clientWidth);

    const canScroll = productsScroll.scrollWidth > productsScroll.clientWidth + 1;

    if (!canScroll) {
        // No scroll needed at all
        scrollLeftBtn.style.visibility = "hidden";
        scrollRightBtn.style.visibility = "hidden";
        return;
    }

    // Hide left button if at the start
    scrollLeftBtn.style.visibility = scrollLeft <= 1 ? "hidden" : "visible";

    // Hide right button if at (or beyond) end
    scrollRightBtn.style.visibility = scrollLeft >= (maxScrollLeft - 1) ? "hidden" : "visible";
}

productsScroll.addEventListener("scroll", updateScrollButtons);
window.addEventListener("resize", updateScrollButtons);

// Load products when page loads
loadProducts().then(updateScrollButtons);
