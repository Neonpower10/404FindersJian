package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Login;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;
import nl.hu.sd.s2.sds2project2025404finders.JwtHelper;

import java.util.Map;
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
    @POST
    public Response login(Login req) {
        if (req == null || isBlank(req.email) || isBlank(req.password)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Vul e-mailadres en wachtwoord in."))
                    .build();
        }
        Registration found = RegistrationRepository.findByEmailAndPassword(req.email, req.password);
        if (found == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("ok", false, "message", "Onjuiste inloggegevens."))
                    .build();
        }

        String role = found.getEmail().equalsIgnoreCase("admin@camping.nl") ? "admin" : "user";
        String token = JwtHelper.createToken(found.getEmail(), role);
        return Response.ok(Map.of(
                "ok", true,
                "message", "Succesvol ingelogd.",
                "id", found.getRegistrationId(),
                "name", found.getName(),
                "surname", found.getSurname(),
                "email", found.getEmail(),
                "role", role,
                "token", token
        )).build();
    }
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}