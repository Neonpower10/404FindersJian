package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;
import nl.hu.sd.s2.sds2project2025404finders.service.EmailService;
import nl.hu.sd.s2.sds2project2025404finders.service.VerificationTokenService;

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
                    .entity(Map.of("ok", false, "message", "Please fill in all required fields."))
                    .build();
        }

        if (RegistrationRepository.findByEmail(registration.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("ok", false, "message", "Email address is already registered."))
                    .build();
        }

        // Generate a unique verification token
        String verificationToken = VerificationTokenService.generateToken();
        
        // Set verified to false (user must still verify)
        registration.setVerified(false);
        
        // Save the verification token
        registration.setVerificationToken(verificationToken);

        // Add the registration to the database
        Registration created = RegistrationRepository.add(registration);
        
        // Send verification email
        boolean emailSent = EmailService.sendVerificationEmail(
            created.getEmail(), 
            created.getName(), 
            verificationToken
        );
        
        // Return a message to the user
        String message = emailSent 
            ? "Registration successful! Check your email for the verification link." 
            : "Registration successful, but there was a problem sending the verification email. Please contact the administrator.";
        
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("ok", true, "message", message, "id", created.getRegistrationId()))
                .build();
    }

    @PUT @Path("/{id}")
    public Response updateRegistration(@PathParam("id") int id, Registration registration) {
        Registration updated = RegistrationRepository.update(id, registration);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/me")
    public Response deleteMyAccount(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        Registration user = RegistrationRepository.findByEmail(email);
        
        if (user == null || user.getEmail().equalsIgnoreCase("admin@camping.nl")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(Map.of("ok", false, "message", "Account kan niet worden verwijderd."))
                    .build();
        }
        
        RegistrationRepository.delete(user.getRegistrationId());
        return Response.ok(Map.of("ok", true, "message", "Account verwijderd.")).build();
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
