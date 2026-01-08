const API_BASE =
    "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api";

export async function getProducts() {
    const res = await fetch(`${API_BASE}/products`);
    return res.json();
}

export async function getProduct(id) {
    const res = await fetch(`${API_BASE}/products/${id}`);
    return res.json();
}

export async function addProduct(product) {
    const res = await fetch(`${API_BASE}/products`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product),
    });
    return res.json();
}

export async function updateProduct(id, product) {
    const res = await fetch(`${API_BASE}/products/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product),
    });
    return res.json();
}

export async function deleteProduct(id) {
    await fetch(`${API_BASE}/products/${id}`, { method: "DELETE" });
}
