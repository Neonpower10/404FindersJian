package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    // temporary in-memory user store
    private static final Map<String, String> USERS = new ConcurrentHashMap<>();

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok(Map.of("ok", true, "msg", "API werkt")).build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterRequest req) {
        if (req == null || isBlank(req.username) || isBlank(req.password) || isBlank(req.email)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AuthResponse(false, "Vul gebruikersnaam, wachtwoord en e-mail in.")).build();
        }
        if (USERS.containsKey(req.username)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new AuthResponse(false, "Gebruikersnaam bestaat al.")).build();
        }
        USERS.put(req.username, req.password);
        return Response.ok(new AuthResponse(true, "Registratie gelukt!")).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        if (req == null || isBlank(req.username) || isBlank(req.password)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AuthResponse(false, "Vul gebruikersnaam en wachtwoord in.")).build();
        }
        String stored = USERS.get(req.username);
        if (stored != null && stored.equals(req.password)) {
            AuthResponse ok = new AuthResponse(true, "Login gelukt!");
            ok.token = "demo-token-" + req.username;
            return Response.ok(ok).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new AuthResponse(false, "Ongeldige inloggegevens.")).build();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
