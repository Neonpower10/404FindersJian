package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;

import java.util.List;
import java.util.Map;

@Path("/registrations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {
    @GET
    public List<Registration> getAllRegistrations() {
        return RegistrationRepository.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getRegistration(@PathParam("id") int id) {
        Registration r = RegistrationRepository.findById(id);
        if (r == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(r).build();
    }

    @POST
    public Response addRegistration(Registration registration) {
        if (registration == null
                || isBlank(registration.getName())
                || isBlank(registration.getSurname())
                || isBlank(registration.getEmail())
                || isBlank(registration.getPassword())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Vul alle verplichte velden in."))
                    .build();
        }

        if (RegistrationRepository.findByEmail(registration.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("ok", false, "message", "E-mailadres is al geregistreerd."))
                    .build();
        }

        Registration created = RegistrationRepository.add(registration);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("ok", true, "message", "Registratie gelukt.", "id", created.getRegistrationId()))
                .build();
    }

    @PUT @Path("/{id}")
    public Response updateRegistration(@PathParam("id") int id, Registration registration) {
        Registration updated = RegistrationRepository.update(id, registration);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE @Path("/{id}")
    public Response deleteRegistration(@PathParam("id") int id) {
        boolean removed = RegistrationRepository.delete(id);
        if (!removed) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
