const url = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/products";

class ProductService {
    async getAllProducts() {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error("Something went wrong with fetching the products.");
        }
        return await response.json();
    }

    async getProductByName(name) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${name}`, {
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch product with name: ${name}`);
        }
        return await response.json();
    }

    async deleteProduct(product) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${product}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to delete product with name: ${product}`);
        }
    }

    async addProduct(product) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify(product),
        });
        if (!response.ok) {
            throw new Error("Failed to add product.");
        }
        return await response.json();
    }

    async updateProduct (product, data) {
        const token = sessionStorage.getItem("jwt");

        const response = await fetch(`${url}/${product}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error("Failed to update product.");
        }
        return await response.json();
    }


}

export default new ProductService();
