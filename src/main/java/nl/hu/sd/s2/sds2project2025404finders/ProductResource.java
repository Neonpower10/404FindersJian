package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Resource class for Product endpoints.
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)  // All responses will be in JSON format
@Consumes(MediaType.APPLICATION_JSON)  // All requests should send JSON data
public class ProductResource {

    /**
     * GET /api/products

     * Gets all products in the system (including products with 0 stock).
     * This endpoint is typically used by admin pages.
     * 
     * @return A list of all products
     */
    @GET
    public List<Product> getAllProducts() {
        return ProductDAO.getAll();
    }

    /**
     * GET /api/products/available
     * -
     * Gets only products that are available (have stock > 0) AND published (visible to customers).
     * -
     * Customers should only see products they can really rent that are published.
     * 
     * @return A list of available and published products (stock > 0 AND published = true)
     */
    @GET
    @Path("/available")
    public List<Product> getAvailableProducts() {
        return ProductDAO.getAvailable();
    }

    /**
     * GET /api/products/published
     * -
     * Gets only products that are published (visible to customers).
     * -
     * Customers should only see published products.
     * 
     * @return A list of published products
     */
    @GET
    @Path("/published")
    public List<Product> getPublishedProducts() {
        return ProductDAO.getPublished();
    }

    /**
     * GET /api/products/{id}
     * -
     * Gets a specific product by its ID.
     * 
     * @param id The ID of the product (extracted from the URL)
     * @return Response with the product, or 404 NOT_FOUND if product doesn't exist
     */
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        Product product = ProductDAO.findById(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(product).build();
    }

    /**
     * POST /api/products
     * -
     * Creates a new product.
     * The product data is sent in the request body as JSON.
     * 
     * @param product The product to create (automatically converted from JSON)
     * @return Response with the created product (with ID assigned), or 400 BAD_REQUEST if validation fails
     */
    @POST
    public Response addProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product name is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (product.getProductDescription() == null || product.getProductDescription().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product description is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (product.getPricePerDay() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Price per day is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (product.getProductStock() < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product stock cannot be negative");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        Product created = ProductDAO.add(product);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * PUT /api/products/{id}

     * Updates an existing product.
     * The product data is sent in the request body as JSON.

     * @param id The ID of the product to update (from the URL)
     * @param updatedProduct The product with updated values (from the request body)
     * @return Response with the updated product, or 404 NOT_FOUND if product doesn't exist
     */
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product updatedProduct) {
        if (updatedProduct.getProductName() == null || updatedProduct.getProductName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product name is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedProduct.getProductDescription() == null || updatedProduct.getProductDescription().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product description is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedProduct.getPricePerDay() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Price per day is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        if (updatedProduct.getProductStock() < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product stock cannot be negative");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        Product existing = ProductDAO.update(id, updatedProduct);
        if (existing == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.ok(existing).build();
    }

    /**
     * DELETE /api/products/{id}
     * -
     * Deletes a product from the system.
     * 
     * @param id The ID of the product to delete (from the URL)
     * @return Response with 204 NO_CONTENT if successful, or 404 NOT_FOUND if product doesn't exist
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        boolean deleted = ProductDAO.delete(id);
        if (!deleted) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.noContent().build();
    }

    /**
     * PUT /api/products/{id}/publish - Publish a product (make it visible to customers)
     * 
     * This endpoint changes the status of a product from "concept" (unpublished) 
     * to "published" (visible to customers).
     * 
     * @param id The ID of the product to publish
     * @return Response with success message or error if product not found
     */
    @PUT
    @Path("/{id}/publish")
    public Response publishProduct(@PathParam("id") int id) {
        boolean published = ProductDAO.publish(id);
        if (!published) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("success", false, "message", "Product with ID " + id + " not found"))
                .build();
        }
        return Response.ok(Map.of("success", true, "message", "Product published successfully"))
            .build();
    }

    /**
     * PUT /api/products/{id}/unpublish - Unpublish a product (make it invisible to customers)
     * 
     * This endpoint changes the status of a product from "published" (visible to customers)
     * back to "concept" (unpublished, not visible to customers).
     * 
     * @param id The ID of the product to unpublish
     * @return Response with success message or error if product not found
     */
    @PUT
    @Path("/{id}/unpublish")
    public Response unpublishProduct(@PathParam("id") int id) {
        boolean unpublished = ProductDAO.unpublish(id);
        if (!unpublished) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("success", false, "message", "Product with ID " + id + " not found"))
                .build();
        }
        return Response.ok(Map.of("success", true, "message", "Product unpublished successfully"))
            .build();
    }
}

