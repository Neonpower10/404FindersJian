const API_URL =
    "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

const container = document.getElementById("productsContainer");

/**
 * Fetch products from backend and render them for campers.
 * Only shows products that exist in the backend.
 */
async function fetchProducts() {
    const res = await fetch(API_URL);
    const products = await res.json();
    renderProducts(products);
}

function renderProducts(products) {
    container.innerHTML = "";

    if (products.length === 0) {
        container.innerHTML = "<p>(Nog) geen producten beschikbaar.</p>";
        return;
    }

    products.forEach(p => {
        const card = document.createElement("div");
        card.className = "product-card";

        card.innerHTML = `
            ${p.productImageUrl ? `<img src="${p.productImageUrl}" alt="${p.productName}">` : ""}
            <h3>${p.productName}</h3>
            <p>€ ${p.pricePerDay} per dag</p>
            <p class="${p.productStock > 0 ? "available" : "unavailable"}">
                ${p.productStock > 0 ? "Beschikbaar" : "Niet beschikbaar"}
            </p>
            ${
            p.productStock > 0
                ? `<a href="productPublicDetail.html?id=${p.id}" class="btn">Lees meer</a>`
                : `<span class="btn disabled">Niet beschikbaar</span>`
        }
        `;
        container.appendChild(card);
    });
}

// Initial load
fetchProducts();
