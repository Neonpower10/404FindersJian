/**
 * Initializes the page once the DOM has fully loaded.
 * Sets up the contact form submission handler.
 */
document.addEventListener("DOMContentLoaded", () => {

    /**
     * Handles the contact form submission.
     *
     * @param {Event} e - The submit event triggered by the form.
     */
    document.getElementById("contactForm").addEventListener("submit", function (e) {
        e.preventDefault(); // Prevents page refresh

        /**
         * @type {FormData}
         * Collects all form field values.
         */
        const formData = new FormData(e.target);

        /**
         * @type {Object}
         * Converts form data into a plain object.
         */
        const data = Object.fromEntries(formData.entries());

        console.log(data); // Logs the data object to the console
    });
});
