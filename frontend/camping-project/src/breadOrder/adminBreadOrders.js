import BreadOrderService from "./service/BreadOrderService.js";

let orders = [];

window.addEventListener("DOMContentLoaded", () => {
    const listContainer = document.getElementById("orders-list");
    const searchInput = document.getElementById("search");

    BreadOrderService.getAllOrders()
        .then((data) => {
            orders = data;
            renderOrders(orders);
        })
        .catch((err) => {
            console.error("Failed to load orders:", err);
        });

    function renderOrders(list) {
        listContainer.innerHTML = "";

        if (list.length === 0) {
            listContainer.innerHTML = "<p class='empty-message'>Geen bestellingen gevonden.</p>";
            return;
        }

        list.forEach((order) => {
            const card = document.createElement("div");
            card.className = "order-card";

            let itemsHtml = "";
            try {
                const items = JSON.parse(order.orderItems || "[]");
                itemsHtml = items.map(item => 
                    `${item.breadName} x${item.quantity} (€${Number(item.price).toFixed(2)} per stuk)`
                ).join(", ");
            } catch (e) {
                itemsHtml = order.orderItems || "Geen details beschikbaar";
            }

            card.innerHTML = `
                <h3>Bestelling #${order.orderId} – ${order.customerFirstName} ${order.customerLastName || ""}</h3>
                <p><strong>Datum:</strong> ${order.orderDate || "Onbekend"}</p>
                <p><strong>Telefoon:</strong> ${order.phoneNumber || "Onbekend"}</p>
                <p><strong>Bestelde items:</strong> ${itemsHtml}</p>
                <p><strong>Totaalprijs:</strong> €${Number(order.totalPrice || 0).toFixed(2)}</p>
                <div class="actions">
                    <button class="deleteOrder" data-id="${order.orderId}">
                        Bestelling verwijderen
                    </button>
                </div>
            `;

            listContainer.appendChild(card);
        });
    }

    listContainer.addEventListener("click", async (event) => {
        const btn = event.target.closest("button");
        if (!btn) return;

        const id = btn.dataset.id;

        if (btn.classList.contains("deleteOrder")) {
            const confirmDelete = confirm("Weet je zeker dat je deze bestelling wilt verwijderen?");
            if (!confirmDelete) return;

            try {
                await BreadOrderService.deleteOrder(id);

                orders = orders.filter((order) => order.orderId != id);

                renderOrders(orders);
            } catch (err) {
                console.error("Delete failed:", err);
                alert("Kan bestelling niet verwijderen.");
            }
        }
    });

    searchInput.addEventListener("input", () => {
        const term = searchInput.value.toLowerCase();
        const filtered = orders.filter((order) =>
            `${order.customerFirstName} ${order.customerLastName || ""}`.toLowerCase().includes(term)
        );
        renderOrders(filtered);
    });
});

