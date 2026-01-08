package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.BreadOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * REST resource class that handles HTTP requests
 * related to bread orders.
 */
@Path("/bread-orders")
public class BreadOrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BreadOrder> getAllOrders() {
        return BreadOrderDAO.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int id) {
        BreadOrder order = BreadOrderDAO.findById(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order not found")
                    .build();
        }

        return Response.ok(order).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        // First, find the order to get its items before deleting
        BreadOrder order = BreadOrderDAO.findById(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order not found")
                    .build();
        }

        // Parse the order items JSON to restore stock
        // The orderItems is stored as a JSON string like:
        // "[{\"breadId\":1,\"breadName\":\"Wit brood\",\"quantity\":2,\"price\":3.50},...]"
        String orderItemsJson = order.getOrderItems();
        
        if (orderItemsJson != null && !orderItemsJson.isEmpty()) {
            try {
                // Parse the JSON string into a list of maps
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> orderItems = mapper.readValue(
                    orderItemsJson, 
                    new TypeReference<List<Map<String, Object>>>() {}
                );
                
                // Restore stock for each item in the order
                for (Map<String, Object> item : orderItems) {
                    // Get breadId and quantity from the order item
                    Object breadIdObj = item.get("breadId");
                    Object quantityObj = item.get("quantity");
                    
                    // Convert to int (handles both Integer and Double from JSON)
                    int breadId;
                    if (breadIdObj instanceof Integer) {
                        breadId = (Integer) breadIdObj;
                    } else if (breadIdObj instanceof Double) {
                        breadId = ((Double) breadIdObj).intValue();
                    } else {
                        // Skip this item if breadId is invalid
                        continue;
                    }
                    
                    int quantity;
                    if (quantityObj instanceof Integer) {
                        quantity = (Integer) quantityObj;
                    } else if (quantityObj instanceof Double) {
                        quantity = ((Double) quantityObj).intValue();
                    } else {
                        // Skip this item if quantity is invalid
                        continue;
                    }
                    
                    // Restore the stock for this bread item
                    BreadDAO.restoreStock(breadId, quantity);
                }
            } catch (Exception e) {
                // If JSON parsing fails, log the error but still delete the order
                // This prevents a corrupted order from blocking deletion
                System.err.println("Error parsing order items when deleting order " + id + ": " + e.getMessage());
            }
        }

        // Now delete the order
        boolean deleted = BreadOrderDAO.delete(id);

        if (!deleted) {
            // This shouldn't happen since we already checked if order exists,
            // but handle it just in case
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete order")
                    .build();
        }

        return Response.noContent().build();
    }
}

