// src/main/java/nl/hu/sd/s2/sds2project2025404finders/BreadDAO.java
package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Bread;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BreadDAO {
    private static final List<Bread> breads = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    // --- CRUD Methods ---
    public static List<Bread> getAll() {
        return breads;
    }

    public static Bread findById(int id) {
        return breads.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Bread add(Bread bread) {
        bread.setId(idCounter.getAndIncrement());
        breads.add(bread);
        return bread;
    }

    public static Bread update(int id, Bread updated) {
        Bread existing = findById(id);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setPrice(updated.getPrice());
            existing.setDescription(updated.getDescription());
            existing.setPhoto(updated.getPhoto());
            // Update stock/inventory when updating bread
            existing.setStock(updated.getStock());
            // Update published status when updating bread
            existing.setPublished(updated.isPublished());
        }
        return existing;
    }

    public static boolean delete(int id) {
        return breads.removeIf(b -> b.getId() == id);
    }

    /**
     * Deducts stock from a bread item when an order is placed.
     * This method checks if there is enough stock before deducting.
     * 
     * @param id The ID of the bread item
     * @param quantity The amount to deduct from stock
     * @return true if stock was successfully deducted, false if not enough stock available
     */
    public static boolean deductStock(int id, int quantity) {
        // Find the bread item by ID
        Bread bread = findById(id);
        
        // If bread doesn't exist, return false
        if (bread == null) {
            return false;
        }
        
        // Check if there is enough stock available
        // We need at least 'quantity' items in stock
        if (bread.getStock() < quantity) {
            // Not enough stock available
            return false;
        }
        
        // Deduct the quantity from current stock
        int newStock = bread.getStock() - quantity;
        bread.setStock(newStock);
        
        // Return true to indicate success
        return true;
    }

    /**
     * Restores stock to a bread item when an order is deleted.
     * This method adds the quantity back to the stock.
     * 
     * @param id The ID of the bread item
     * @param quantity The amount to restore to stock
     * @return true if stock was successfully restored, false if bread not found
     */
    public static boolean restoreStock(int id, int quantity) {
        // Find the bread item by ID
        Bread bread = findById(id);
        
        // If bread doesn't exist, return false
        if (bread == null) {
            return false;
        }
        
        // Add the quantity back to current stock
        int newStock = bread.getStock() + quantity;
        bread.setStock(newStock);
        
        // Return true to indicate success
        return true;
    }

    /**
     * Gets all published breads (visible to customers).
     * Only breads with published = true are returned.
     * 
     * @return List of published bread items
     */
    public static List<Bread> getPublished() {
        // Filter the list to only include published breads
        return breads.stream()
                .filter(b -> b.isPublished())
                .toList();
    }

    /**
     * Publishes a bread item (makes it visible to customers).
     * Sets the published status to true.
     * 
     * @param id The ID of the bread item to publish
     * @return true if bread was found and published, false if bread not found
     */
    public static boolean publish(int id) {
        Bread bread = findById(id);
        if (bread == null) {
            return false;
        }
        // Set published status to true
        bread.setPublished(true);
        return true;
    }

    /**
     * Unpublishes a bread item (makes it invisible to customers, moves it back to concept).
     * Sets the published status to false.
     * 
     * @param id The ID of the bread item to unpublish
     * @return true if bread was found and unpublished, false if bread not found
     */
    public static boolean unpublish(int id) {
        Bread bread = findById(id);
        if (bread == null) {
            return false;
        }
        // Set published status to false (back to concept)
        bread.setPublished(false);
        return true;
    }
}