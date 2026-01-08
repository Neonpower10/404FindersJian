import ProductService from "./service/ProductService.js";

const productList = document.querySelector(".productList")

let products = []

function getAllProducts() {
    ProductService.getAllProducts()
        .then((data) => {
            products = data;
            renderProducts(products);
        })
        .catch((err) => {
            console.error("Failed to load products:", err);
        });
}

function renderProducts(list) {
    list.forEach((p) => {
        const productCard = document.createElement("div");
        productCard.className = "productCard";

        productCard.innerHTML = `
        <h1>${p.productName}</h1>
        <h3>€${p.pricePerDay} per dag</h3>
        <section class="productImage"><img src=${p.imageUrl} alt="Product afbeelding" class="productImage"></section>
        <button class="readMore" data-name="${p.productName}">Lees meer</button>
        `

        const readMoreButton = productCard.querySelector(".readMore");
        readMoreButton.addEventListener("click", () => {
            openProductDetails(p.productName);
        });

        productList.appendChild(productCard);
    })
}

function openProductDetails(product) {
    window.location.href = `/productDetails.html?productName=${product}`
}

window.addEventListener("DOMContentLoaded", () => {
    getAllProducts()
})