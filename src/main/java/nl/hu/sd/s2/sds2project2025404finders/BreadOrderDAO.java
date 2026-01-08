// src/main/java/nl/hu/sd/s2/sds2project2025404finders/BreadOrderDAO.java
package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.BreadOrder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data Access Object (DAO) for managing bread orders.
 * It stores all bread orders in memory (in a list).
 * 
 * This class provides methods to:
 * - Get all orders
 * - Find an order by ID
 * - Add a new order
 * - Delete an order
 */
public class BreadOrderDAO {
    // This list stores all bread orders in memory
    private static final List<BreadOrder> orders = new ArrayList<>();
    
    // Counter to generate unique order IDs
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    
    /**
     * Gets all bread orders.
     * 
     * @return List of all bread orders
     */
    public static List<BreadOrder> getAll() {
        return orders;
    }
    
    /**
     * Finds a bread order by its ID.
     * 
     * @param id The order ID to search for
     * @return The BreadOrder if found, or null if not found
     */
    public static BreadOrder findById(int id) {
        return orders.stream()
                .filter(order -> order.getOrderId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Adds a new bread order to the list.
     * Automatically assigns a unique ID to the order.
     * 
     * @param order The bread order to add
     * @return The added order with its assigned ID
     */
    public static BreadOrder add(BreadOrder order) {
        // Set a unique ID for the new order
        order.setOrderId(idCounter.getAndIncrement());
        // Add the order to the list
        orders.add(order);
        return order;
    }
    
    /**
     * Deletes a bread order by its ID.
     * 
     * @param id The ID of the order to delete
     * @return true if the order was found and deleted, false if not found
     */
    public static boolean delete(int id) {
        return orders.removeIf(order -> order.getOrderId() == id);
    }
}

