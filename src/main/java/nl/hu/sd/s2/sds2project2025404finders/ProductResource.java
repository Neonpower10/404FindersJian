package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.util.List;
import java.util.Map;

@Path("/rental/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public List<Product> getAllProducts() {
        return ProductRepository.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") int id) {
        Product p = ProductRepository.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("ok", false, "message", "Product niet gevonden."))
                    .build();
        }
        return Response.ok(p).build();
    }

    public Response getProductById(int id) {
        return getProduct(id);
    }
}
