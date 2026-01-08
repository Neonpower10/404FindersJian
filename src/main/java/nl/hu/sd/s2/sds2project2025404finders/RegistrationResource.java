package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;

import java.util.List;

@Path("/registrations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @GET
    public List<Registration> getAllRegistrations() {return RegistrationRepository.getAll();}

    @GET
    @Path("/{id}")
    public Response getRegistration(@PathParam("id") int id) {
        Registration f = RegistrationRepository.findById(id);
        if (f == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(f).build();
    }

    @POST
    public Response addRegistration(Registration registration) {
        Registration created = RegistrationRepository.add(registration);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRegistration(@PathParam("id") int registrationId, Registration registration) {
        Registration updated = RegistrationRepository.update(registrationId, registration);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRegistration(@PathParam("id") int id) {
        boolean removed = RegistrationRepository.delete(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}