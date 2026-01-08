/**
 * Base URL for the bread orders API.
 * @type {string}
 */
const url = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/bread-orders";

/**
 * Service class to interact with the bread orders API.
 * Provides methods to fetch orders from the backend.
 */
class BreadOrderService {

    /**
     * Fetches all bread orders from the backend API.
     *
     * @async
     * @throws {Error} Throws an error if the fetch request fails.
     * @returns {Promise<Array<Object>>} A promise that resolves to an array of order objects.
     */
    async getAllOrders() {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error("Something went wrong with fetching the orders.");
        }
        return await response.json();
    }

    /**
     * Fetches a single bread order by id.
     *
     * @param {number|string} id - The order ID.
     * @returns {Promise<Object>} The order object.
     */
    async getOrderById(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch order with id ${id}.`);
        }
        return await response.json();
    }

    async deleteOrder(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to delete order with id ${id}.`);
        }
    }
}

/**
 * Singleton instance of BreadOrderService for use across the frontend.
 */
export default new BreadOrderService();

