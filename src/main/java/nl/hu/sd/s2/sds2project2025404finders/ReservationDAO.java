package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Reservation entities.
 * -
 * This DAO manages all reservations.
 * -
 * This class provides methods to:
 * - Get all reservations or find specific reservations
 * - Add, update, or delete reservations
 * - Check product availability before making a reservation
 */
public class ReservationDAO {
    private static final List<Reservation> reservations = new ArrayList<>();
    private static int idCounter = 1;

    /**
     * Gets all reservations in the system.
     *  for admin pages that need to show all reservations.
     * 
     * @return A list containing all reservations
     */
    public static List<Reservation> getAll() {
        return reservations;
    }

    /**
     * Finds a reservation by its ID.
     * 
     * @param id The ID of the reservation to find
     * @return The reservation with the matching ID, or null if not found
     */
    public static Reservation findById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == id) {
                return reservation;
            }
        }
        return null;
    }

    /**
     * Finds all reservations for a specific product.
     * 
     * @param productId The ID of the product
     * @return A list of all reservations for that product
     */
    public static List<Reservation> findByProductId(int productId) {
        List<Reservation> productReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getProductId() == productId) {
                productReservations.add(reservation);
            }
        }
        return productReservations;
    }

    /**
     * Checks if a product is available for reservation.
     * 
     * @param productId The ID of the product to check
     * @param startDate The start date of the reservation
     * @param endDate The end date of the reservation
     * @param amount How many items the customer wants to reserve
     * @return true if the product is available, false if not enough stock
     */
    public static boolean checkAvailability(int productId, LocalDate startDate, LocalDate endDate, int amount) {
        Product product = ProductDAO.findById(productId);
        if (product == null) {
            return false;
        }
        return product.getProductStock() >= amount;
    }

    /**
     * Adds a new reservation to system.
     * IMPORTANT: This method checks if there's enough stock before adding the reservation.
     * If there's not enough stock, the reservation is NOT added and null is returned.
     * -
     * If the reservation is successfully added, the products stock is automatically reduced.
     * 
     * @param reservation The reservation to add (should not have an ID set yet)
     * @return The reservation with an ID assigned, or null if not enough stock
     */
    public static Reservation add(Reservation reservation) {
        boolean available = checkAvailability(
            reservation.getProductId(),
            reservation.getReservationStartDate(),
            reservation.getReservationEndDate(),
            reservation.getProductAmount()
        );
        
        if (!available) {
            return null;
        }
        
        Product product = ProductDAO.findById(reservation.getProductId());
        if (product == null) {
            return null;
        }
        
        reservation.setPricePerDay(product.getPricePerDay());
        reservation.setReservationId(idCounter);
        idCounter = idCounter + 1;
        
        boolean stockDeducted = ProductDAO.deductStock(
            reservation.getProductId(),
            reservation.getProductAmount()
        );
        
        if (!stockDeducted) {
            return null;
        }
        
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Updates an existing reservation.
     * IMPORTANT: If the amount increases, we check if there's enough stock first.
     * If not enough stock, the update fails and returns null.
     * 
     * @param id The ID of the reservation to update
     * @param updatedReservation The reservation object with new values
     * @return The updated reservation, or null if the reservation was not found or not enough stock
     */
    public static Reservation update(int id, Reservation updatedReservation) {
        // Find the existing reservation
        Reservation existing = findById(id);
        if (existing == null) {
            return null;
        }
        
        // Check if the amount (quantity) changed
        int oldAmount = existing.getProductAmount();
        int newAmount = updatedReservation.getProductAmount();
        
        // If amount increased, check if we have enough stock
        if (newAmount > oldAmount) {
            int extraNeeded = newAmount - oldAmount;
            boolean available = checkAvailability(
                existing.getProductId(),
                updatedReservation.getReservationStartDate(),
                updatedReservation.getReservationEndDate(),
                extraNeeded
            );
            
            // If not enough stock, don't allow the increase
            if (!available) {
                return null;
            }
        }
        
        // Adjust the stock based on the difference
        if (oldAmount != newAmount) {
            int difference = newAmount - oldAmount;
            
            if (difference > 0) {
                // Amount increased: take the extra items from stock
                ProductDAO.deductStock(existing.getProductId(), difference);
            } else {
                // Amount decreased: give back the items
                int itemsToReturn = -difference;
                ProductDAO.addStock(existing.getProductId(), itemsToReturn);
            }
        }
        
        // Update all the fields of the reservation
        existing.setProductId(updatedReservation.getProductId());
        existing.setReservationStartDate(updatedReservation.getReservationStartDate());
        existing.setReservationEndDate(updatedReservation.getReservationEndDate());
        existing.setProductAmount(updatedReservation.getProductAmount());
        existing.setCustomerName(updatedReservation.getCustomerName());
        
        // Update the price per day (in case the product changed)
        Product product = ProductDAO.findById(updatedReservation.getProductId());
        if (product != null) {
            existing.setPricePerDay(product.getPricePerDay());
        }
        
        return existing;
    }

    /**
     * Deletes a reservation from the system.
     * -
     * IMPORTANT: When a reservation is deleted, we must add the stock back to the product.
     * 
     * @param id The ID of the reservation to delete
     * @return true if the reservation was found and deleted, false if not found
     */
    public static boolean delete(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == id) {
                ProductDAO.addStock(reservation.getProductId(), reservation.getProductAmount());
                reservations.remove(reservation);
                return true;
            }
        }
        return false;
    }
}

