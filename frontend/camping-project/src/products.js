fetch(`${apiBase()}/rental/products`, {
    headers: authHeaders()
})
    .then(async res => {
        if (!res.ok) throw new Error(await res.text());
        return res.json();
    })
    .then(products => {
        const container = document.getElementById("product-list");
        container.innerHTML = "";

        products.forEach(p => {
            const div = document.createElement("div");
            div.innerHTML = `
        <h3>${p.productName}</h3>
        <p>${p.productDescription ?? ""}</p>
        <button onclick="viewProduct(${p.productId})">Lees meer</button>
        <hr>
      `;
            container.appendChild(div);
        });
    })
    .catch(err => {
        document.getElementById("product-list").innerText =
            "Fout bij laden (misschien Login vereist?): " + err.message;
    });

function viewProduct(id) {
    window.location.href = `product.html?id=${id}`;
}
