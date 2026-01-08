const API_URL =
    "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

let products = [];
let editId = null;
let currentImageBase64 = null;

const listEl = document.getElementById("productList");
const form = document.getElementById("productForm");
const imageInput = document.getElementById("image");

function fileToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}

imageInput.addEventListener("change", async () => {
    if (imageInput.files[0]) {
        currentImageBase64 = await fileToBase64(imageInput.files[0]);
    }
});

async function fetchProducts() {
    const res = await fetch(API_URL);
    products = await res.json();
    renderProducts(products);
}

function renderProducts(list) {
    listEl.innerHTML = "";

    list.forEach(p => {
        const li = document.createElement("li");
        li.innerHTML = `
            <strong>${p.name}</strong><br>
            Beschikbaar: ${p.totalStock}<br>
            <button onclick="editProduct(${p.id})">Bewerk</button>
            <button class="danger" onclick="deleteProduct(${p.id})">
              Product verwijderen
            </button>
        `;
        listEl.appendChild(li);
    });
}

function filterProducts() {
    const q = document.getElementById("search").value.toLowerCase();
    renderProducts(
        products.filter(p => p.name.toLowerCase().includes(q))
    );
}

window.editProduct = function(id) {
    const p = products.find(p => p.id === id);
    editId = id;

    document.getElementById("name").value = p.name;
    document.getElementById("description").value = p.description;
    document.getElementById("paymentPeriod").value = p.paymentPeriod;
    document.getElementById("price").value = p.pricePerPeriod;
    document.getElementById("stock").value = p.totalStock;

    currentImageBase64 = p.image;
    imageInput.value = "";
};

window.deleteProduct = async function(id) {
    if (!confirm("Product verwijderen?")) return;

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
        }
    });

    fetchProducts();
};

form.addEventListener("submit", async e => {
    e.preventDefault();

    if (!editId && !currentImageBase64) {
        alert("Upload een afbeelding");
        return;
    }

    const body = {
        name: name.value.trim(),
        description: description.value.trim(),
        image: currentImageBase64,
        paymentPeriod: paymentPeriod.value,
        pricePerPeriod: parseFloat(price.value),
        totalStock: parseInt(stock.value)
    };

    const method = editId ? "PUT" : "POST";
    const url = editId ? `${API_URL}/${editId}` : API_URL;

    await fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
        },
        body: JSON.stringify(body)
    });

    form.reset();
    editId = null;
    currentImageBase64 = null;
    fetchProducts();
});

fetchProducts();
