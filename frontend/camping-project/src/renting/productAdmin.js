import ProductService from "./service/ProductService.js";
import ReservationService from "./service/ReservationService.js";

const itemsListDiv = document.querySelector(".itemsList")
const productForm = document.querySelector(".productForm")
const myProductsBtn = document.querySelector(".myProducts")
const myReservations = document.querySelector(".allReservations")

let products = [];
let reservations = [];

function getProducts() {
    ProductService.getAllProducts()
        .then((data) => {
            products = data;
            fillMyProductsList(products)
        })
        .catch((err) => {
            console.error("Failed to load products:", err);
        });
}

function getReservations() {
    ReservationService.getAllProducts()
        .then((data) => {
            reservations = data;
            fillMyReservationsList(reservations)
        })
        .catch((err) => {
            console.error("Failed to load reservations:", err);
        });
}

function fillMyProductsList(allProducts) {
    itemsListDiv.innerHTML = ""
    const allItems = document.createElement("div")
    allItems.className = "allItems"

    allProducts.forEach((product) => {
        const productItem = document.createElement("div")
        productItem.className = "productItem"

        const productName = document.createElement("h3")
        productName.textContent = product.productName
        const productAmountInStock = document.createElement("p")
        productAmountInStock.textContent = "Aantal beschikbaar: " + product.productStock

        const editProduct = document.createElement("button")
        editProduct.className = "editProduct"
        editProduct.textContent = "Bewerk product"
        editProduct.addEventListener("click", () => {
            window.location.href = `/productEdit.html?productName=${product.productName}`
        })

        const deleteProduct = document.createElement("button")
        deleteProduct.className = "deleteProduct"
        deleteProduct.textContent = "Verwijder product"
        deleteProduct.addEventListener("click", async () => {
            const confirmDelete = confirm("Weet je zeker dat je dit product wilt verwijderen?")
            if (!confirmDelete) return

            try {
                await ProductService.deleteProduct(product.productName)
                getProducts()
            } catch (err) {
                console.error("Delete failed: " + err)
                alert("Product kan niet verwijderd worden")
            }
        })

        allItems.appendChild(productItem)
        productItem.appendChild(productName)
        productItem.appendChild(productAmountInStock)
        productItem.appendChild(editProduct)
        productItem.appendChild(deleteProduct)
    })

    itemsListDiv.appendChild(allItems)
}

function fillMyReservationsList(allReservations) {
    itemsListDiv.innerHTML = ""
    const allItems = document.createElement("div")
    allItems.className = "allItems"

    allProducts.forEach((product) => {
        const productItem = document.createElement("div")
        productItem.className = "productItem"

        const productName = document.createElement("h3")
        productName.textContent = product.productName
        const productAmountInStock = document.createElement("p")
        productAmountInStock.textContent = "Aantal beschikbaar: " + product.productStock

        const editProduct = document.createElement("button")
        editProduct.className = "editProduct"
        editProduct.textContent = "Bewerk product"
        editProduct.addEventListener("click", () => {
            window.location.href = `/productEdit.html?productName=${product.productName}`
        })

        const deleteProduct = document.createElement("button")
        deleteProduct.className = "deleteProduct"
        deleteProduct.textContent = "Verwijder product"
        deleteProduct.addEventListener("click", async () => {
            const confirmDelete = confirm("Weet je zeker dat je dit product wilt verwijderen?")
            if (!confirmDelete) return

            try {
                await ProductService.deleteProduct(product.productName)
                getProducts()
            } catch (err) {
                console.error("Delete failed: " + err)
                alert("Product kan niet verwijderd worden")
            }
        })

        allItems.appendChild(productItem)
        productItem.appendChild(productName)
        productItem.appendChild(productAmountInStock)
        productItem.appendChild(editProduct)
        productItem.appendChild(deleteProduct)
    })

    itemsListDiv.appendChild(allItems)
}

function getFormData() {
    const formData = new FormData(productForm);
    const file = formData.get("productImage");

    return new Promise((resolve, reject) => {
        if (!file) {
            resolve({
                productName: formData.get('productName'),
                productDescription: formData.get('productDescription'),
                pricePerDay: Number(formData.get('pricePerDay')),
                productStock: Number(formData.get('productStock')),
                productImageUrl: ""
            });
        }

        const reader = new FileReader();
        reader.onload = () => {
            resolve({
                productName: formData.get('productName'),
                productDescription: formData.get('productDescription'),
                pricePerDay: Number(formData.get('pricePerDay')),
                productStock: Number(formData.get('productStock')),
                productImageUrl: reader.result
            });
        };
        reader.onerror = (err) => reject(err);
        reader.readAsDataURL(file);
    });
}


function addProduct() {
    productForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        try {
            const product = await getFormData();
            await ProductService.addProduct(product);
            productForm.reset();
            getProducts();
        } catch (err) {
            console.error(err);
            alert("Product kan niet toegevoegd worden");
        }
    });
}

// function switchTab() {
//     const allItemsDiv = document.querySelector(".allItems")
//     myProductsBtn.addEventListener("click", () => {
//         if (allItemsDiv.style.display === "none" && ) {
//             allItemsDiv.style.display = "block";
//         }
//     })
// }

window.addEventListener("DOMContentLoaded", () => {
    getProducts()
    addProduct()
})