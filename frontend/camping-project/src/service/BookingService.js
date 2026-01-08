/**
 * Base URL for the bookings API.
 * @type {string}
 */
const url = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/bookings";

/**
 * Service class to interact with the bookings API.
 * Provides methods to fetch bookings from the backend.
 */
class BookingService {

    /**
     * Fetches all bookings from the backend API.
     *
     * @async
     * @throws {Error} Throws an error if the fetch request fails.
     * @returns {Promise<Array<Object>>} A promise that resolves to an array of booking objects.
     */
    async getAllBookings() {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Something went wrong with fetching the bookings.");
        }
        return await response.json();
    }
}

/**
 * Singleton instance of BookingService for use across the frontend.
 */
export default new BookingService();
