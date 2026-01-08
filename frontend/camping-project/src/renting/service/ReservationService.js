const url = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/reservations";

class ReservationService {
    async getAllReservations() {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error("Something went wrong with fetching the reservations.");
        }
        return await response.json();
    }

    async getReservationById(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch reservation with id: ${id}`);
        }
        return await response.json();
    }

    async deleteReservation(id) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to delete reservation with id: ${id}`);
        }
    }

    async addReservation(reservation) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify(reservation),
        });
        if (!response.ok) {
            throw new Error("Failed to add reservation.");
        }
        return await response.json();
    }

    async updateReservation (id, data) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error("Failed to update reservation.");
        }
        return await response.json();
    }


}

export default new ProductService();
