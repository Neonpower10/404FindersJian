const params = new URLSearchParams(window.location.search);
const productId = params.get("id");

if (!productId) {
    document.getElementById("product").innerText = "Geen productId in de URL.";
} else {
    fetch(`${apiBase()}/rental/products/${productId}`, {
        headers: authHeaders()
    })
        .then(async res => {
            if (!res.ok) throw new Error(await res.text());
            return res.json();
        })
        .then(p => {
            document.getElementById("product").innerHTML = `
        <h3>${p.productName}</h3>
        <p>${p.productDescription ?? ""}</p>
        <p>Prijs per dag: €${p.pricePerDay}</p>
      `;
        })
        .catch(err => {
            document.getElementById("product").innerText =
                "Fout bij product laden: " + err.message;
        });
}

function rent() {
    const body = {
        productId: Number(productId),
        startDate: document.getElementById("start").value,
        endDate: document.getElementById("end").value,
        productAmount: Number(document.getElementById("amount").value)
    };

    fetch(`${apiBase()}/rental/reservations`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...authHeaders()
        },
        body: JSON.stringify(body)
    })
        .then(async res => {
            // backend stuurt soms text bij errors (Login vereist)
            const text = await res.text();
            try {
                return JSON.parse(text);
            } catch {
                throw new Error(text);
            }
        })
        .then(data => {
            document.getElementById("result").innerText =
                data.ok ? `Reservering gelukt! (ID: ${data.reservationId})` : data.message;
        })
        .catch(err => {
            document.getElementById("result").innerText =
                "Fout bij reserveren: " + err.message;
        });
}
