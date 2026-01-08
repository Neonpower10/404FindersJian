// Backend API URL - same as breadManagement.js
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/breads";

// Cart array to store selected breads
let cart = [];
// Store all breads with their stock information
let allBreads = [];

// DOM elements
const breadsListElement = document.getElementById("breadsList");
const loadingText = document.getElementById("loadingText");
const errorText = document.getElementById("errorText");
const cartItemsElement = document.getElementById("cartItems");
const cartTotalElement = document.getElementById("cartTotal");
const checkoutBtn = document.getElementById("checkoutBtn");

// Load only published breads from backend (visible to customers)
async function loadBreads() {
    try {
        loadingText.style.display = "block";
        errorText.style.display = "none";
        
        // Use /published endpoint to only get breads that are published (visible to customers)
        const response = await fetch(API_URL + "/published");
        
        if (!response.ok) {
            throw new Error("Kon broodjes niet ophalen");
        }
        
        const breads = await response.json();
        // Store breads globally so we can check stock later
        allBreads = breads;
        loadingText.style.display = "none";
        showBreads(breads);
    } catch (error) {
        console.error("Error loading breads:", error);
        loadingText.style.display = "none";
        errorText.style.display = "block";
    }
}

// Display breads in the grid
function showBreads(breads) {
    breadsListElement.innerHTML = "";
    
    if (breads.length === 0) {
        breadsListElement.innerHTML = "<p class='empty-text'>Geen broodjes beschikbaar</p>";
        return;
    }
    
    breads.forEach(function(bread) {
        const breadCard = document.createElement("div");
        breadCard.className = "bread-card";
        
        // Get stock value, default to 0 if not set
        const voorraad = bread.stock != null ? bread.stock : 0;
        // Check if bread is out of stock
        const isOutOfStock = voorraad <= 0;
        
        // Create button HTML - disable if out of stock
        let buttonHTML = "";
        if (isOutOfStock) {
            // Button is disabled when out of stock
            buttonHTML = `<button class="btn-add-to-cart" disabled style="opacity: 0.5; cursor: not-allowed;">
                Uitverkocht
            </button>`;
        } else {
            // Button is enabled when stock is available
            buttonHTML = `<button class="btn-add-to-cart" onclick="addToCart(${bread.id}, '${bread.name}', ${bread.price}, '${bread.photo || ''}', ${voorraad})">
                Toevoegen aan winkelwagen
            </button>`;
        }
        
        breadCard.innerHTML = `
            <div class="bread-image">
                <img src="${bread.photo || '/public/icons/campingMoodImage.jpg'}" alt="${bread.name || 'Broodje'}">
            </div>
            <div class="bread-info">
                <h3>${bread.name || 'Naamloos broodje'}</h3>
                <p class="bread-description">${bread.description || 'Geen beschrijving'}</p>
                <p class="bread-price">€${Number(bread.price || 0).toFixed(2)}</p>
                <p class="bread-stock">Voorraad: ${voorraad}</p>
                ${buttonHTML}
            </div>
        `;
        
        breadsListElement.appendChild(breadCard);
    });
}

// Add bread to cart
function addToCart(id, naam, prijs, foto, voorraad) {
    // Find the bread in allBreads to check current stock
    const bread = allBreads.find(function(b) {
        return b.id === id;
    });
    
    // If bread not found, show error
    if (!bread) {
        alert("Broodje niet gevonden.");
        return;
    }
    
    // Get current stock (use the voorraad parameter or from bread object)
    const currentStock = voorraad != null ? voorraad : (bread.stock != null ? bread.stock : 0);
    
    // Check if bread already in cart
    const existingItem = cart.find(function(item) {
        return item.id === id;
    });
    
    // Calculate how many we want to add (1 more if already in cart)
    const quantityToAdd = existingItem ? existingItem.quantity + 1 : 1;
    
    // Check if there is enough stock available
    if (quantityToAdd > currentStock) {
        alert("Niet genoeg voorraad beschikbaar. Beschikbaar: " + currentStock);
        return;
    }
    
    if (existingItem) {
        // If already in cart, increase quantity
        existingItem.quantity = existingItem.quantity + 1;
    } else {
        // If not in cart, add new item
        // Note: cart uses Dutch field names for display, but we keep them for UI consistency
        cart.push({
            id: id,
            naam: naam,  // Keep Dutch for display in cart
            prijs: prijs,  // Keep Dutch for display in cart
            foto: foto,  // Keep Dutch for display in cart
            quantity: 1
        });
    }
    
    updateCartDisplay();
}

// Remove bread from cart
function removeFromCart(id) {
    cart = cart.filter(function(item) {
        return item.id !== id;
    });
    updateCartDisplay();
}

// Update quantity of item in cart
function updateQuantity(id, change) {
    const item = cart.find(function(item) {
        return item.id === id;
    });
    
    if (item) {
        // Find the bread to check stock
        const bread = allBreads.find(function(b) {
            return b.id === id;
        });
        
        if (bread) {
            const currentStock = bread.stock != null ? bread.stock : 0;
            const newQuantity = item.quantity + change;
            
            // Check if new quantity exceeds stock
            if (newQuantity > currentStock) {
                alert("Niet genoeg voorraad beschikbaar. Beschikbaar: " + currentStock);
                return;
            }
        }
        
        item.quantity = item.quantity + change;
        
        // Remove if quantity becomes 0 or less
        if (item.quantity <= 0) {
            removeFromCart(id);
        } else {
            updateCartDisplay();
        }
    }
}

// Update cart display
function updateCartDisplay() {
    cartItemsElement.innerHTML = "";
    
    if (cart.length === 0) {
        cartItemsElement.innerHTML = "<p class='empty-cart'>Je winkelwagen is leeg</p>";
        cartTotalElement.textContent = "0.00";
        checkoutBtn.disabled = true;
        return;
    }
    
    let total = 0;
    
    cart.forEach(function(item) {
        const itemTotal = item.prijs * item.quantity;
        total = total + itemTotal;
        
        const cartItem = document.createElement("div");
        cartItem.className = "cart-item";
        
        cartItem.innerHTML = `
            <div class="cart-item-image">
                <img src="${item.foto || '/public/icons/campingMoodImage.jpg'}" alt="${item.naam}">
            </div>
            <div class="cart-item-info">
                <h4>${item.naam}</h4>
                <p>€${Number(item.prijs).toFixed(2)} per stuk</p>
            </div>
            <div class="cart-item-controls">
                <button class="btn-quantity" onclick="updateQuantity(${item.id}, -1)">-</button>
                <span class="quantity">${item.quantity}</span>
                <button class="btn-quantity" onclick="updateQuantity(${item.id}, 1)">+</button>
                <button class="btn-remove" onclick="removeFromCart(${item.id})">Verwijderen</button>
            </div>
            <div class="cart-item-total">
                <p>€${Number(itemTotal).toFixed(2)}</p>
            </div>
        `;
        
        cartItemsElement.appendChild(cartItem);
    });
    
    cartTotalElement.textContent = total.toFixed(2);
    checkoutBtn.disabled = false;
}

// Handle checkout
checkoutBtn.addEventListener("click", async function() {
    if (cart.length === 0) {
        alert("Je winkelwagen is leeg!");
        return;
    }
    
    // Calculate total price
    const total = cart.reduce(function(sum, item) {
        return sum + (item.prijs * item.quantity);
    }, 0);
    
    // Prepare order data for backend
    // Backend expects: [{"id": 1, "quantity": 2}, {"id": 3, "quantity": 1}]
    const orderItems = cart.map(function(item) {
        return {
            id: item.id,
            quantity: item.quantity
        };
    });
    
    try {
        // Send order to backend
        const response = await fetch(API_URL + "/order", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(orderItems)
        });
        
        const result = await response.json();
        
        if (!response.ok || !result.success) {
            // Show error message from backend
            alert("Bestelling mislukt: " + (result.message || "Onbekende fout"));
            return;
        }
        
        // Order was successful
        alert("Bedankt voor je bestelling!\n\nTotaal: €" + total.toFixed(2) + "\n\nJe bestelling is verzonden.");
        
        // Clear cart after successful checkout
        cart = [];
        updateCartDisplay();
        
        // Reload breads to update stock display
        await loadBreads();
        
    } catch (error) {
        console.error("Error processing order:", error);
        alert("Er ging iets mis bij het verwerken van je bestelling. Probeer het opnieuw.");
    }
});

// Make functions available globally for onclick handlers
window.addToCart = addToCart;
window.removeFromCart = removeFromCart;
window.updateQuantity = updateQuantity;

// Load breads when page loads
loadBreads();

