package nl.hu.sd.s2.sds2project2025404finders.facilities;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;

import java.util.List;

@Path("/facilities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FacilityResource {

    private final FacilityRepository repository = new FacilityRepository();

    @GET
    public List<Facility> getAllFacilities() {
        return repository.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getFacility(@PathParam("id") int id) {
        Facility f = repository.findById(id);
        if (f == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(f).build();
    }

    @POST
    public Response addFacility(Facility facility) {
        Facility created = repository.add(facility);
        if (created == null) {
            return Response.serverError().build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateFacility(@PathParam("id") int id, Facility facility) {
        Facility updated = repository.update(id, facility);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFacility(@PathParam("id") int id) {
        boolean removed = repository.delete(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
