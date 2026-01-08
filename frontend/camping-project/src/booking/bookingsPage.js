import BookingService from "./service/BookingService.js";

/**
 * DOM Elements
 */
const minusBtn = document.getElementById('minBtn');
const plusBtn = document.getElementById('plusBtn');
const amountInput = document.getElementById('amountInput');
const nextBtn = document.querySelector('.nextBtn');
const confirmBtn = document.querySelector('.confirmBtn');
const tabs = document.querySelectorAll('.progressSteps li');
const bookingConfirmation = document.querySelector('.bookingConfirmation');
const steps = document.querySelectorAll('.step');
const form = document.querySelector('.bookingForm');

let currentStep = 0;
let amountPeopleValue = 1;

/**
 * Decrease the amount of people by 1 if greater than 1.
 * Shows an alert if user tries to go below 1.
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
 * Increase the amount of people by 1.
 */
plusBtn.addEventListener('click', () => {
    amountPeopleValue++;
    amountInput.textContent = amountPeopleValue;
});

/**
 * Show a specific booking step and update UI elements accordingly.
 *
 * @param {number} index - Index of the step to show.
 */
function showStep(index) {
    steps.forEach((step, i) => step.classList.toggle('show', i === index));
    tabs.forEach((tab, i) => tab.classList.toggle('active', i === index));

    nextBtn.style.display = (index === steps.length - 1) ? 'none' : 'inline-block';
    confirmBtn.style.display = (index === steps.length - 1) ? 'inline-block' : 'none';
}

/**
 * Collect booking data from the form and amount selection.
 *
 * @returns {Object} Booking data object containing user input values.
 */
function getBookingData() {
    const formData = new FormData(form);

    return {
        gender: formData.get('geslacht'),
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        email: formData.get('email'),
        phoneNumber: formData.get('phoneNumber'),
        streetName: formData.get('streetName'),
        houseNumber: formData.get('houseNumber'),
        postcode: formData.get('postcode'),
        place: formData.get('place'),
        country: formData.get('country'),
        startDate: formData.get('dateFrom'),
        endDate: formData.get('dateTo'),
        amountPersons: amountPeopleValue
    };
}

/**
 * Display the booking confirmation summary on the page.
 *
 * @param {Object} data - The booking data to show in the confirmation section.
 */
function showConfirmation(data) {
    bookingConfirmation.innerHTML = `
        <h2>Gegevens overzicht</h2>
        <p><strong>Naam:</strong> ${data.firstName} ${data.lastName}</p>
        <p><strong>Geslacht:</strong> ${data.gender || '-'}</p>
        <p><strong>Email:</strong> ${data.email}</p>
        <p><strong>Telefoon:</strong> ${data.phoneNumber}</p>
        <p><strong>Adres:</strong> ${data.streetName} ${data.houseNumber}, ${data.postcode} ${data.place}, ${data.country}</p>
        <p><strong>Datum verblijf:</strong> ${data.startDate} t/m ${data.endDate}</p>
        <p><strong>Aantal personen:</strong> ${data.amountPersons}</p>
    `;
    bookingConfirmation.classList.add('show');
    bookingConfirmation.scrollIntoView({behavior: 'smooth'});
}

/**
 * Handle next step button click:
 * - Validates current step inputs.
 * - Moves to the next step if valid.
 * - Shows confirmation when reaching the last step.
 */
nextBtn.addEventListener('click', () => {
    const currentInputs = steps[currentStep].querySelectorAll('input');

    for (let input of currentInputs) {
        if (!input.checkValidity()) {
            input.reportValidity();
            return;
        }
    }

    currentStep++;
    showStep(currentStep);

    if (currentStep === steps.length - 1) {
        const data = getBookingData();
        showConfirmation(data);
    }
});

/**
 * Handle form submission:
 * - Checks login (JWT required!)
 * - Sends booking data to BookingService.
 * - Displays success or error message.
 */
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const data = getBookingData();

    const booking = {
        gender: data.gender,
        personFirstName: data.firstName,
        personLastName: data.lastName,
        email: data.email,
        phoneNumber: data.phoneNumber,
        streetName: data.streetName,
        houseNumber: data.houseNumber,
        postcode: data.postcode,
        place: data.place,
        country: data.country,
        campPlace: "Standaard plek",
        startDate: data.startDate,
        endDate: data.endDate,
        amountPersons: data.amountPersons
    };

    try {
        const response = await BookingService.addBooking(booking);
        window.location.href = "bookingConfirmation.html"
    } catch (error) {
        alert("Er ging iets mis 😢");
    }
});

/**
 * Initialize the first step on page load.
 */
showStep(currentStep);
