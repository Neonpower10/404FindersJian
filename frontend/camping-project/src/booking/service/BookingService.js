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
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error("Something went wrong with fetching the bookings.");
        }
        return await response.json();
    }

    /**
     * Fetches a single booking by id.
     *
     * @param {number|string} id - The booking ID.
     * @returns {Promise<Object>} The booking object.
     */
    async getBookingById(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch booking with id ${id}.`);
        }
        return await response.json();
    }

    async deleteBooking(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to delete booking with id ${id}.`);
        }
    }

    /**
     * Sends a new booking to the backend.
     * @param {Object} booking - The booking data to send.
     */
    async addBooking(booking) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify(booking),
        });
        if (!response.ok) {
            throw new Error("Failed to add booking.");
        }
        return await response.json();
    }

    async updateBooking(id, data) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""},
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error("Failed to update booking.");
        }
        return await response.json();
    }


}

/**
 * Singleton instance of BookingService for use across the frontend.
 */
export default new BookingService();
