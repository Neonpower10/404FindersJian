package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.util.Map;

@Path("/admin/rental/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentalAdminProductResource {

    @POST
    public Response addProduct(Product product) {
        if (product == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Ongeldig product."))
                    .build();
        }

        Product created = ProductRepository.add(product);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("ok", true, "id", created.getProductId()))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product product) {
        Product updated = ProductRepository.update(id, product);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("ok", false, "message", "Product niet gevonden."))
                    .build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        boolean removed = ProductRepository.delete(id);
        return removed
                ? Response.ok(Map.of("ok", true)).build()
                : Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("ok", false, "message", "Product niet gevonden."))
                .build();
    }
}
