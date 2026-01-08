/**
 * Selects all checkbox elements within the .switch container.
 * These checkboxes are used to toggle modules on or off.
 *
 * @type {NodeListOf<HTMLInputElement>}
 */
const checkboxes = document.querySelectorAll('.switch input[type="checkbox"]');

/**
 * Iterate through all checkboxes and attach an event listener
 * that reacts when their state (checked/unchecked) changes.
 */
checkboxes.forEach((checkbox) => {

    /**
     * Event handler for when a checkbox state changes.
     *
     * - Retrieves the name of the module from the closest .module container.
     * - Logs to the console whether the module is turned ON or OFF.
     */
    checkbox.addEventListener('change', () => {

        /**
         * Gets the module name from the corresponding .module element.
         * Defaults to "Unknown module" if no name is found.
         *
         * @type {string}
         */
        const moduleName = checkbox.closest('.module').querySelector('span')?.textContent || "Unknown module";

        if (checkbox.checked) {
            console.log(`${moduleName} is ON`);
        } else {
            console.log(`${moduleName} is OFF`);
        }
    });
});
