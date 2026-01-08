// src/main/java/nl/hu/sd/s2/sds2project2025404finders/BreadResource.java
package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Bread;
import java.util.List;

@Path("/breads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreadResource {

    // GET /api/breads - Tüm ekmekleri getir
    @GET
    public List<Bread> getAllBreads() {
        return BreadDAO.getAll();
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
}