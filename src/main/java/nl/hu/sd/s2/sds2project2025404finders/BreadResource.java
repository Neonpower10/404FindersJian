// src/main/java/nl/hu/sd/s2/sds2project2025404finders/BreadResource.java
package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Bread;
import nl.hu.sd.s2.sds2project2025404finders.domain.BreadOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Path("/breads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreadResource {

    // GET /api/breads - Get all breads (for admin - includes both published and unpublished)
    @GET
    public List<Bread> getAllBreads() {
        return BreadDAO.getAll();
    }

    // GET /api/breads/published - Get only published breads (for customers)
    @GET
    @Path("/published")
    public List<Bread> getPublishedBreads() {
        return BreadDAO.getPublished();
    }

    // GET /api/breads/{id} - Tek bir ekmeği getir
    @GET
    @Path("/{id}")
    public Response getBreadById(@PathParam("id") int id) {
        Bread bread = BreadDAO.findById(id);
        if (bread == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(bread).build();
    }

    // POST /api/breads - Yeni ekmek ekle
    @POST
    public Response addBread(Bread bread) {
        Bread created = BreadDAO.add(bread);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // PUT /api/breads/{id} - Ekmek güncelle
    @PUT
    @Path("/{id}")
    public Response updateBread(@PathParam("id") int id, Bread updatedBread) {
        Bread existing = BreadDAO.update(id, updatedBread);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(existing).build();
    }

    // DELETE /api/breads/{id} - Ekmek sil
    @DELETE
    @Path("/{id}")
    public Response deleteBread(@PathParam("id") int id) {
        boolean deleted = BreadDAO.delete(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    /**
     * POST /api/breads/order - Process a bread order and deduct stock
     * 
     * This endpoint receives an order (list of bread items with quantities),
     * checks if there is enough stock for each item, and deducts the stock.
     * 
     * Expected JSON format:
     * [
     *   {"id": 1, "quantity": 2},
     *   {"id": 3, "quantity": 1}
     * ]
     * 
     * @param orderItems List of maps containing "id" (bread ID) and "quantity" (amount to order)
     * @return Response with success message or error if insufficient stock
     */
    @POST
    @Path("/order")
    public Response processOrder(List<Map<String, Object>> orderItems) {
        // First, check if all items have enough stock
        // We do this before deducting anything, so if one item fails, nothing is deducted
        for (Map<String, Object> item : orderItems) {
            // Get the bread ID from the order item
            // The frontend sends "id" as a number, but JSON might convert it
            Object idObj = item.get("id");
            int breadId;
            
            // Convert to int (handles both Integer and Double from JSON)
            if (idObj instanceof Integer) {
                breadId = (Integer) idObj;
            } else if (idObj instanceof Double) {
                breadId = ((Double) idObj).intValue();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", "Invalid bread ID format"))
                    .build();
            }
            
            // Get the quantity from the order item
            Object quantityObj = item.get("quantity");
            int quantity;
            
            // Convert to int
            if (quantityObj instanceof Integer) {
                quantity = (Integer) quantityObj;
            } else if (quantityObj instanceof Double) {
                quantity = ((Double) quantityObj).intValue();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", "Invalid quantity format"))
                    .build();
            }
            
            // Check if bread exists
            Bread bread = BreadDAO.findById(breadId);
            if (bread == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("success", false, "message", "Bread with ID " + breadId + " not found"))
                    .build();
            }
            
            // Check if there is enough stock
            if (bread.getStock() < quantity) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", 
                        "Not enough stock for bread: " + bread.getName() + 
                        ". Available: " + bread.getStock() + ", Requested: " + quantity))
                    .build();
            }
        }
        
        // If we get here, all items have enough stock
        // Now we'll:
        // 1. Calculate total price
        // 2. Create order items with bread names and prices
        // 3. Deduct stock
        // 4. Save the order
        
        double totalPrice = 0.0;
        List<Map<String, Object>> orderItemsWithDetails = new ArrayList<>();
        
        // First pass: calculate total and create detailed order items
        for (Map<String, Object> item : orderItems) {
            // Get bread ID and quantity
            Object idObj = item.get("id");
            int breadId;
            if (idObj instanceof Integer) {
                breadId = (Integer) idObj;
            } else {
                breadId = ((Double) idObj).intValue();
            }
            
            Object quantityObj = item.get("quantity");
            int quantity;
            if (quantityObj instanceof Integer) {
                quantity = (Integer) quantityObj;
            } else {
                quantity = ((Double) quantityObj).intValue();
            }
            
            // Get bread details
            Bread bread = BreadDAO.findById(breadId);
            double itemPrice = bread.getPrice() * quantity;
            totalPrice += itemPrice;
            
            // Create detailed order item with bread name and price
            Map<String, Object> detailedItem = Map.of(
                "breadId", breadId,
                "breadName", bread.getName(),
                "quantity", quantity,
                "price", bread.getPrice()
            );
            orderItemsWithDetails.add(detailedItem);
        }
        
        // Second pass: deduct stock
        for (Map<String, Object> item : orderItems) {
            Object idObj = item.get("id");
            int breadId;
            if (idObj instanceof Integer) {
                breadId = (Integer) idObj;
            } else {
                breadId = ((Double) idObj).intValue();
            }
            
            Object quantityObj = item.get("quantity");
            int quantity;
            if (quantityObj instanceof Integer) {
                quantity = (Integer) quantityObj;
            } else {
                quantity = ((Double) quantityObj).intValue();
            }
            
            // Deduct the stock
            boolean success = BreadDAO.deductStock(breadId, quantity);
            
            if (!success) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("success", false, "message", "Failed to deduct stock for bread ID " + breadId))
                    .build();
            }
        }
        
        // Convert order items to JSON string
        String orderItemsJson = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            orderItemsJson = mapper.writeValueAsString(orderItemsWithDetails);
        } catch (Exception e) {
            // If JSON conversion fails, use a simple string representation
            orderItemsJson = orderItemsWithDetails.toString();
        }
        
        // Get current date in DD-MM-YYYY format
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String orderDate = today.format(formatter);
        
        // Create and save the bread order
        // For now, we use placeholder customer info since the frontend doesn't collect it yet
        // Later, you can update the frontend to collect customer name and phone number
        BreadOrder order = new BreadOrder();
        order.setCustomerFirstName("Gast"); // Placeholder - can be improved later
        order.setCustomerLastName(""); // Placeholder
        order.setPhoneNumber("Onbekend"); // Placeholder
        order.setEmail(null); // Optional
        order.setOrderDate(orderDate);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItemsJson);
        
        // Save the order
        BreadOrderDAO.add(order);
        
        // All stock has been successfully deducted and order saved
        return Response.ok(Map.of("success", true, "message", "Order processed successfully", "orderId", order.getOrderId()))
            .build();
    }

    /**
     * PUT /api/breads/{id}/publish - Publish a bread item (make it visible to customers)
     * 
     * This endpoint changes the status of a bread from "concept" (unpublished) 
     * to "published" (visible to customers).
     * 
     * @param id The ID of the bread item to publish
     * @return Response with success message or error if bread not found
     */
    @PUT
    @Path("/{id}/publish")
    public Response publishBread(@PathParam("id") int id) {
        boolean published = BreadDAO.publish(id);
        if (!published) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("success", false, "message", "Bread with ID " + id + " not found"))
                .build();
        }
        return Response.ok(Map.of("success", true, "message", "Bread published successfully"))
            .build();
    }

    /**
     * PUT /api/breads/{id}/unpublish - Unpublish a bread item (make it invisible to customers)
     * 
     * This endpoint changes the status of a bread from "published" (visible to customers)
     * back to "concept" (unpublished, not visible to customers).
     * 
     * @param id The ID of the bread item to unpublish
     * @return Response with success message or error if bread not found
     */
    @PUT
    @Path("/{id}/unpublish")
    public Response unpublishBread(@PathParam("id") int id) {
        boolean unpublished = BreadDAO.unpublish(id);
        if (!unpublished) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("success", false, "message", "Bread with ID " + id + " not found"))
                .build();
        }
        return Response.ok(Map.of("success", true, "message", "Bread unpublished successfully"))
            .build();
    }
}