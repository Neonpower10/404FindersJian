const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

// Get product ID from URL
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get("id");

const productImage = document.getElementById("productImage");
const productDescription = document.getElementById("productDescription");
const rentalForm = document.getElementById("rentalForm");
const quantityInput = document.getElementById("quantity");
const decreaseBtn = document.getElementById("decreaseBtn");
const increaseBtn = document.getElementById("increaseBtn");
const daysInput = document.getElementById("days");
const weeksInput = document.getElementById("weeks");
const monthsInput = document.getElementById("months");
const startDateInput = document.getElementById("startDate");

let currentProduct = null;

// Set minimum date to today
const today = new Date();
let month = today.getMonth() + 1;
let day = today.getDate();
if (month < 10) month = "0" + month;
if (day < 10) day = "0" + day;
const todayString = today.getFullYear() + "-" + month + "-" + day;
startDateInput.setAttribute('min', todayString);

// Load product data from API
async function loadProduct() {
    if (!productId) {
        document.getElementById("errorText").style.display = "block";
        return;
    }
    
    try {
        const response = await fetch(API_URL + "/" + productId);
        if (!response.ok) {
            document.getElementById("errorText").style.display = "block";
            return;
        }
        currentProduct = await response.json();
        
        let imageUrl = currentProduct.productImageUrl;
        if (!imageUrl) {
            imageUrl = '/public/icons/campingMoodImage.jpg';
        }
        productImage.src = imageUrl;
        
        let description = currentProduct.productDescription;
        if (!description) {
            description = "Geen beschrijving beschikbaar.";
        }
        productDescription.textContent = description;
    } catch (error) {
        document.getElementById("errorText").style.display = "block";
    }
}

// Quantity buttons
decreaseBtn.addEventListener("click", function() {
    const current = parseInt(quantityInput.value) || 1;
    if (current > 1) {
        quantityInput.value = current - 1;
    }
});

increaseBtn.addEventListener("click", function() {
    const current = parseInt(quantityInput.value) || 1;
    quantityInput.value = current + 1;
});

// Handle form submit
rentalForm.addEventListener("submit", function(event) {
    event.preventDefault();
    
    const days = parseInt(daysInput.value) || 0;
    const weeks = parseInt(weeksInput.value) || 0;
    const months = parseInt(monthsInput.value) || 0;
    const totalDays = days + (weeks * 7) + (months * 30);
    
    if (totalDays === 0) {
        alert("Selecteer minimaal 1 dag om te huren.");
        return;
    }
    
    if (!startDateInput.value) {
        alert("Selecteer een startdatum.");
        return;
    }
    
    // Calculate end date
    const startDate = new Date(startDateInput.value);
    const endDate = new Date(startDate);
    endDate.setDate(endDate.getDate() + totalDays - 1);
    
    // Format end date to YYYY-MM-DD
    let endMonth = endDate.getMonth() + 1;
    let endDay = endDate.getDate();
    if (endMonth < 10) endMonth = "0" + endMonth;
    if (endDay < 10) endDay = "0" + endDay;
    const endDateString = endDate.getFullYear() + "-" + endMonth + "-" + endDay;
    
    // Store data in sessionStorage to pass to next page
    const rentalData = {
        productId: currentProduct.productId,
        productName: currentProduct.productName,
        pricePerDay: currentProduct.pricePerDay,
        quantity: parseInt(quantityInput.value),
        startDate: startDateInput.value,
        endDate: endDateString,
        totalDays: totalDays
    };
    
    sessionStorage.setItem("rentalData", JSON.stringify(rentalData));
    window.location.href = "/productRentalConfirmation.html";
});

// Load product when page loads
loadProduct();
