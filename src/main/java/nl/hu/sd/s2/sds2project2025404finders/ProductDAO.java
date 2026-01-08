package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Product entities.
 * 
 * A DAO is responsible for all database operations. For now, we use an ArrayList
 * (a list) stored in memory. Later, this can be replaced with a real database.
 * 
 * This class provides methods to:
 * - Get all products or find a specific product
 * - Add, update, or delete products
 * - Manage product stock (inventory)
 */
public class ProductDAO {
    private static final List<Product> products = new ArrayList<>();
    private static int idCounter = 1;

    /**
     * Gets all products in the system.
     * This is useful for admin pages that need to show all products.
     * 
     * @return A list containing all products
     */
    public static List<Product> getAll() {
        return products;
    }

    /**
     * Gets only products that are available (have stock > 0) AND published (visible to customers).
     * This is useful for customer pages - they should only see products they can rent that are published.
     * 
     * @return A list containing only products with stock > 0 AND published = true
     */
    public static List<Product> getAvailable() {
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isInStock() && product.isPublished()) {
                availableProducts.add(product);
            }
        }
        return availableProducts;
    }

    /**
     * Gets all published products (visible to customers).
     * Only products with published = true are returned.
     * 
     * @return A list containing only published products
     */
    public static List<Product> getPublished() {
        List<Product> publishedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isPublished()) {
                publishedProducts.add(product);
            }
        }
        return publishedProducts;
    }

    /**
     * Finds a product by its ID.
     * 
     * @param id The ID of the product to find
     * @return The product with the matching ID, or null if not found
     */
    public static Product findById(int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Adds a new product to the system.
     * The product will automatically get a unique ID assigned.
     * 
     * @param product The product to add (should not have an ID set yet)
     * @return The same product, but now with an ID assigned
     */
    public static Product add(Product product) {
        product.setProductId(idCounter);
        idCounter = idCounter + 1;
        products.add(product);
        return product;
    }

    /**
     * Updates an existing product.
     * Finds the product by ID and updates all its fields.
     * 
     * @param id The ID of the product to update
     * @param updatedProduct The product object with new values
     * @return The updated product, or null if the product was not found
     */
    public static Product update(int id, Product updatedProduct) {
        Product existing = findById(id);
        if (existing != null) {
            existing.setProductName(updatedProduct.getProductName());
            existing.setProductImageUrl(updatedProduct.getProductImageUrl());
            existing.setProductDescription(updatedProduct.getProductDescription());
            existing.setPricePerDay(updatedProduct.getPricePerDay());
            existing.setProductStock(updatedProduct.getProductStock());
            // Update published status when updating product
            existing.setPublished(updatedProduct.isPublished());
        }
        return existing;
    }

    /**
     * Deletes a product from the system.
     * 
     * @param id The ID of the product to delete
     * @return true if the product was found and deleted, false if not found
     */
    public static boolean delete(int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * Deducts stock from a product when a reservation is made.
     * This method checks if there is enough stock before deducting.
     * 
     * @param id The ID of the product
     * @param amount The amount to deduct from stock
     * @return true if stock was successfully deducted, false if product not found or not enough stock
     */
    public static boolean deductStock(int id, int amount) {
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        if (product.getProductStock() < amount) {
            return false;
        }
        int newStock = product.getProductStock() - amount;
        product.setProductStock(newStock);
        return true;
    }

    /**
     * Adds stock back to a product.
     * This is used when a reservation is cancelled or deleted.
     * 
     * @param id The ID of the product
     * @param amount The amount to add to stock
     * @return true if stock was successfully added, false if product not found
     */
    public static boolean addStock(int id, int amount) {
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        int newStock = product.getProductStock() + amount;
        product.setProductStock(newStock);
        return true;
    }

    /**
     * Publishes a product (makes it visible to customers).
     * Sets the published status to true.
     * 
     * @param id The ID of the product to publish
     * @return true if product was found and published, false if product not found
     */
    public static boolean publish(int id) {
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        // Set published status to true
        product.setPublished(true);
        return true;
    }

    /**
     * Unpublishes a product (makes it invisible to customers, moves it back to concept).
     * Sets the published status to false.
     * 
     * @param id The ID of the product to unpublish
     * @return true if product was found and unpublished, false if product not found
     */
    public static boolean unpublish(int id) {
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        // Set published status to false (back to concept)
        product.setPublished(false);
        return true;
    }
}

