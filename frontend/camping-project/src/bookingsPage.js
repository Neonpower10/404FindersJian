/**
 * Get DOM elements for interactive buttons and inputs
 */
const minusBtn = document.getElementById('minBtn');
const plusBtn = document.getElementById('plusBtn');
const amountInput = document.getElementById('amountInput');
const nextBtn = document.querySelector('.nextBtn');
const bookingDetails = document.querySelector('.bookingDetails');
const costOverview = document.querySelector('.costOverview');
const tabs = document.querySelectorAll('.progressSteps li');
const confirmBtn = document.querySelector('.confirmBtn');
const personData = document.querySelector('.personDataForm');
const dateFrom = document.getElementById('dateFrom');
const dateTo = document.getElementById('dateTo');
const amountPeople = document.getElementById('amountInput');
const bookingConfirmation = document.querySelector('.bookingConfirmation');

/**
 * Current number of persons in the booking. Starts with 1
 * @type {number}
 */
let amountPeopleValue = 1;

/**
 * Current active tab index in the booking process
 * @type {number}
 */
let currentTab = 0;

/**
 * Decrease the number of persons
 */
minusBtn.addEventListener('click', () => {
    if (amountPeopleValue > 1) {
        amountPeopleValue--;
        amountInput.textContent = amountPeopleValue;
    } else {
        alert('Aantal personen kan niet minder dan 1 zijn.');
    }
});

/**
 * Increase the number of persons
 */
plusBtn.addEventListener('click', () => {
    if (amountPeopleValue >= 0) {
        amountPeopleValue++;
        amountInput.textContent = amountPeopleValue;
    }
});

/**
 * Move to the next tab in the booking process
 */
function updateTabs() {
    if (currentTab < tabs.length - 1) {
        tabs[currentTab].classList.remove('active');
        currentTab++;
        tabs[currentTab].classList.add('active');
    }
}

/**
 * Collect all booking data from the form and inputs
 * @returns {Object} Booking data object
 */
function getBookingData() {
    return {
        gender: personData.querySelector('input[name="geslacht"]:checked')?.value,
        firstName: personData.firstName.value,
        lastName: personData.lastName.value,
        email: personData.email.value,
        phoneNumber: personData.phoneNumber.value,
        streetName: personData.streetName.value,
        houseNumber: personData.houseNumber.value,
        postcode: personData.postcode.value,
        place: personData.place.value,
        land: personData.land.value,
        arrival: dateFrom.value,
        departure: dateTo.value,
        amountPeople: amountPeopleValue
    };
}

/**
 * Display a booking confirmation with the collected data
 * @param {Object} bookingData - The booking data to display
 */
function showConfirmation(bookingData) {
    bookingConfirmation.innerHTML = `
        <h2>Confirmation</h2>
        <p><strong>Name:</strong> ${bookingData.firstName} ${bookingData.lastName}</p>
        <p><strong>Gender:</strong> ${bookingData.gender || "-"}</p>
        <p><strong>Email:</strong> ${bookingData.email}</p>
        <p><strong>Phone:</strong> ${bookingData.phoneNumber}</p>
        <p><strong>Adres:</strong> ${bookingData.streetName} ${bookingData.houseNumber}, ${bookingData.postcode} ${bookingData.place}, ${bookingData.land}</p>
        <p><strong>Arrival:</strong> ${bookingData.arrival}</p>
        <p><strong>Departure:</strong> ${bookingData.departure}</p>
        <p><strong>Amount of people:</strong> ${bookingData.amountPeople}</p>
    `;
    bookingConfirmation.classList.add('show');
    bookingConfirmation.scrollIntoView({behavior: 'smooth'});
}

/**
 * Toggle visibility of buttons depending on the current tab
 */
function toggleButtons() {
    if (currentTab === tabs.length - 1) {
        nextBtn.style.display = "none";
        confirmBtn.style.display = "inline-block";
    } else {
        confirmBtn.style.display = "none";
    }
}

/**
 * Scroll the booking details into view after animation completes
 */
function scrollAfterAnimation() {
    const onTransitionEnd = () => {
        bookingDetails.scrollIntoView({behavior: 'smooth', block: 'start'});
        bookingDetails.removeEventListener('transitionend', onTransitionEnd);
    };
    bookingDetails.addEventListener('transitionend', onTransitionEnd);
}

/**
 * Handle click on the "next" button: show details, update tab, toggle buttons,
 * and show confirmation if at the last step
 */
nextBtn.addEventListener('click', () => {
    bookingDetails.classList.add('show');
    costOverview.classList.add('show');

    updateTabs();
    toggleButtons();

    if (currentTab === tabs.length - 1) {
        const data = getBookingData();
        showConfirmation(data);
    }

    scrollAfterAnimation();
});

/**
 * Handle click on the "confirm" button: log booking data and show alert
 */
confirmBtn.addEventListener('click', (e) => {
    e.preventDefault();

    const bookingData = getBookingData();

    console.log('Boekingsgegevens:', bookingData);
    alert('Boeking bevestigd! Dank u voor uw reservering.');
});
