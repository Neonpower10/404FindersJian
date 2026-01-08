// Backend API URL - same as breadManagement.js
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/breads";

// Cart array to store selected breads
let cart = [];

// DOM elements
const breadsListElement = document.getElementById("breadsList");
const loadingText = document.getElementById("loadingText");
const errorText = document.getElementById("errorText");
const cartItemsElement = document.getElementById("cartItems");
const cartTotalElement = document.getElementById("cartTotal");
const checkoutBtn = document.getElementById("checkoutBtn");

// Load all breads from backend
async function loadBreads() {
    try {
        loadingText.style.display = "block";
        errorText.style.display = "none";
        
        const response = await fetch(API_URL);
        
        if (!response.ok) {
            throw new Error("Kon broodjes niet ophalen");
        }
        
        const breads = await response.json();
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
        
        breadCard.innerHTML = `
            <div class="bread-image">
                <img src="${bread.foto || '/public/icons/campingMoodImage.jpg'}" alt="${bread.naam || 'Broodje'}">
            </div>
            <div class="bread-info">
                <h3>${bread.naam || 'Naamloos broodje'}</h3>
                <p class="bread-description">${bread.beschrijving || 'Geen beschrijving'}</p>
                <p class="bread-price">€${Number(bread.prijs || 0).toFixed(2)}</p>
                <button class="btn-add-to-cart" onclick="addToCart(${bread.id}, '${bread.naam}', ${bread.prijs}, '${bread.foto || ''}')">
                    Toevoegen aan winkelwagen
                </button>
            </div>
        `;
        
        breadsListElement.appendChild(breadCard);
    });
}

// Add bread to cart
function addToCart(id, naam, prijs, foto) {
    // Check if bread already in cart
    const existingItem = cart.find(function(item) {
        return item.id === id;
    });
    
    if (existingItem) {
        // If already in cart, increase quantity
        existingItem.quantity = existingItem.quantity + 1;
    } else {
        // If not in cart, add new item
        cart.push({
            id: id,
            naam: naam,
            prijs: prijs,
            foto: foto,
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
checkoutBtn.addEventListener("click", function() {
    if (cart.length === 0) {
        alert("Je winkelwagen is leeg!");
        return;
    }
    
    const total = cart.reduce(function(sum, item) {
        return sum + (item.prijs * item.quantity);
    }, 0);
    
    alert("Bedankt voor je bestelling!\n\nTotaal: €" + total.toFixed(2) + "\n\nJe bestelling is verzonden.");
    
    // Clear cart after checkout
    cart = [];
    updateCartDisplay();
});

// Make functions available globally for onclick handlers
window.addToCart = addToCart;
window.removeFromCart = removeFromCart;
window.updateQuantity = updateQuantity;

// Load breads when page loads
loadBreads();

