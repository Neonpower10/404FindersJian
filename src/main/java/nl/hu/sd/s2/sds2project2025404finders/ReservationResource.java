package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import nl.hu.sd.s2.sds2project2025404finders.service.EmailService;
import nl.hu.sd.s2.sds2project2025404finders.loginandregistration.RegistrationRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Resource class for Reservation endpoints.
 * 
 * This class handles HTTP requests related to reservations.
 * The frontend (JavaScript) can send requests to these endpoints to:
 * - Get all reservations or a specific reservation
 * - Create, update, or delete reservations
 * 
 * IMPORTANT: When creating a reservation, we check if there's enough stock available.
 * 
 * The @Path annotation tells Jersey that this class handles requests to "/reservations"
 * So all endpoints will be at /api/reservations
 */
@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)  // All responses will be in JSON format
@Consumes(MediaType.APPLICATION_JSON)  // All requests should send JSON data
public class ReservationResource {

    /**
     * GET /api/reservations
     * 
     * Gets all reservations in the system.
     * This endpoint is typically used by admin pages.
     * 
     * @return A list of all reservations
     */
    @GET
    public List<Reservation> getAllReservations() {
        return ReservationDAO.getAll();
    }

    /**
     * GET /api/reservations/{id}
     * 
     * Gets a specific reservation by its ID.
     * The {id} in the path is a path parameter - it's part of the URL.
     * Example: GET /api/reservations/1 gets the reservation with ID 1
     * 
     * @param id The ID of the reservation (extracted from the URL)
     * @return Response with the reservation, or 404 NOT_FOUND if reservation doesn't exist
     */
    @GET
    @Path("/{id}")
    public Response getReservationById(@PathParam("id") int id) {
        Reservation reservation = ReservationDAO.findById(id);
        if (reservation == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.ok(reservation).build();
    }

    /**
     * GET /api/reservations/product/{productId}
     * 
     * Gets all reservations for a specific product.
     * This is useful when you want to see all reservations for a product.
     * 
     * @param productId The ID of the product
     * @return A list of all reservations for that product
     */
    @GET
    @Path("/product/{productId}")
    public List<Reservation> getReservationsByProductId(@PathParam("productId") int productId) {
        return ReservationDAO.findByProductId(productId);
    }

    /**
     * POST /api/reservations
     * 
     * Creates a new reservation.
     * The reservation data is sent in the request body as JSON.
     * 
     * IMPORTANT: This method checks if there's enough stock before creating the reservation.
     * If there's not enough stock, the reservation is NOT created and an error is returned.
     * 
     * Validates that required fields are present:
     * - productId (required)
     * - reservationStartDate (required)
     * - reservationEndDate (required)
     * - productAmount (required, must be > 0)
     * - customerName (required)
     * 
     * @param reservation The reservation to create (automatically converted from JSON)
     * @return Response with the created reservation (with ID assigned), or error if validation/availability fails
     */
    @POST
    public Response addReservation(Reservation reservation, @Context SecurityContext securityContext) {
        if (reservation.getProductId() <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Valid product ID is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (reservation.getReservationStartDate() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation start date is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (reservation.getReservationEndDate() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation end date is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (reservation.getReservationEndDate().isBefore(reservation.getReservationStartDate())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "End date must be after start date");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (reservation.getProductAmount() <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product amount must be greater than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (reservation.getCustomerName() == null || reservation.getCustomerName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Customer name is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        Reservation created = ReservationDAO.add(reservation);
        if (created == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Not enough stock available for this product");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        // Get the product details for the email
        Product product = ProductDAO.findById(created.getProductId());
        if (product != null) {
            // Try to get email from SecurityContext (logged-in user)
            String customerEmail = null;
            try {
                if (securityContext != null && securityContext.getUserPrincipal() != null) {
                    String userEmail = securityContext.getUserPrincipal().getName();
                    // Verify that this user exists and get their email
                    var registration = RegistrationRepository.findByEmail(userEmail);
                    if (registration != null) {
                        customerEmail = registration.getEmail();
                    }
                }
            } catch (Exception e) {
                // Ignore - email is optional
                System.err.println("Could not retrieve email from SecurityContext: " + e.getMessage());
            }
            
            // Send confirmation email if we have an email address
            if (customerEmail != null && !customerEmail.trim().isEmpty()) {
                EmailService.sendProductReservationConfirmation(created, product, customerEmail);
            } else {
                System.out.println("No email address available for reservation " + created.getReservationId() + ". Email not sent.");
            }
        }
        
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * PUT /api/reservations/{id}
     * 
     * Updates an existing reservation.
     * The reservation data is sent in the request body as JSON.
     * 
     * IMPORTANT: If the amount changes, the stock is automatically adjusted.
     * 
     * @param id The ID of the reservation to update (from the URL)
     * @param updatedReservation The reservation with updated values (from the request body)
     * @return Response with the updated reservation, or error if validation fails or not found
     */
    @PUT
    @Path("/{id}")
    public Response updateReservation(@PathParam("id") int id, Reservation updatedReservation) {
        if (updatedReservation.getProductId() <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Valid product ID is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedReservation.getReservationStartDate() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation start date is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedReservation.getReservationEndDate() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation end date is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedReservation.getReservationEndDate().isBefore(updatedReservation.getReservationStartDate())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "End date must be after start date");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedReservation.getProductAmount() <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product amount must be greater than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedReservation.getCustomerName() == null || updatedReservation.getCustomerName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Customer name is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        Reservation existing = ReservationDAO.update(id, updatedReservation);
        if (existing == null) {
            Reservation check = ReservationDAO.findById(id);
            if (check == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Reservation not found");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Not enough stock available for the updated amount");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
        }
        return Response.ok(existing).build();
    }

    /**
     * DELETE /api/reservations/{id}
     * 
     * Deletes a reservation from the system.
     * 
     * IMPORTANT: When a reservation is deleted, the product's stock is automatically
     * increased (the items become available again).
     * 
     * @param id The ID of the reservation to delete (from the URL)
     * @return Response with 204 NO_CONTENT if successful, or 404 NOT_FOUND if reservation doesn't exist
     */
    @DELETE
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") int id) {
        boolean deleted = ReservationDAO.delete(id);
        if (!deleted) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Reservation not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.noContent().build();
    }
}

