const API_BASE =
    "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

const params = new URLSearchParams(window.location.search);
const productId = params.get("id");

let product;

async function fetchProduct() {
    const res = await fetch(`${API_BASE}/products/${productId}`);
    product = await res.json();
    renderProduct();
}

function renderProduct() {
    document.getElementById("productName").innerText = product.productName;
    document.getElementById("productDescription").innerText = product.productDescription;
    document.getElementById("price").innerText = product.pricePerDay;
    document.getElementById("stock").innerText = product.productStock;

    if (product.productImageUrl) {
        document.getElementById("productImage").src = product.productImageUrl;
    }

    if (product.productStock <= 0) {
        document.getElementById("reserveBtn").disabled = true;
    }
}

document.getElementById("reserveBtn").addEventListener("click", async () => {
    const reservation = {
        reservationStartDate: document.getElementById("startDate").value,
        reservationEndDate: document.getElementById("endDate").value,
        productAmount: parseInt(document.getElementById("amount").value),
        product: product
    };

    await fetch(`${API_BASE}/reservations`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reservation)
    });

    alert("Reservering succesvol geplaatst!");
    window.location.href = "productsPage.html";
});

fetchProduct();
