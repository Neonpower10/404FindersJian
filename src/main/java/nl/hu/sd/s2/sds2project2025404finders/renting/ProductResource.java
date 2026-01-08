package nl.hu.sd.s2.sds2project2025404finders.renting;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.util.List;

@Path("/products")
public class ProductResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        return Product.getProducts();
    }

    @GET
    @Path("/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("productName") String productName) {
        for (Product p : Product.getProducts()) {
            if (p.getProductName().equals(productName)) {
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Product niet gevonden").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        Product.getProducts().add(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @DELETE
    @Path("/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam ("productName") String productName) {
        List<Product> products = Product.getProducts();

        for  (Product product : products) {
            if (product.getProductName().equals(productName)) {
                products.remove(product);
                return Response.noContent().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Product niet gevonden").build();
    }

    @PATCH
    @Path("/{productName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam ("productName") String productName, Product updatedProduct) {
        List<Product> products = Product.getProducts();

        for (Product p : products) {
            if (p.getProductName().equals(productName)) {
                if (updatedProduct.getProductName() != null) {
                    p.setProductName(updatedProduct.getProductName());
                }
                if (updatedProduct.getProductImageUrl() != null) {
                    p.setProductImageUrl(updatedProduct.getProductImageUrl());
                }
                if (updatedProduct.getProductDescription() != null) {
                    p.setProductDescription(updatedProduct.getProductDescription());
                }
                if (updatedProduct.getPricePerDay() != null) {
                    p.setPricePerDay(updatedProduct.getPricePerDay());
                }

                if (updatedProduct.getProductStock() != 0) {
                    p.setProductStock(updatedProduct.getProductStock());
                }

                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Product niet gevonden").build();
    }
}
