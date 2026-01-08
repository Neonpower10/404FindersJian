package nl.hu.sd.s2.sds2project2025404finders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx) {

        // Standard everyone is guest -> user = null
        SecurityUser user = null;

        // Read the Authorization header
        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Only continue if the header starts with "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Cut off the "Bearer" part and keep only the token
            String token = authHeader.substring("Bearer ".length()).trim();
            try {
                // Build a JWT parser that uses the same KEY as JwtHelper to verify the signature
                var parser = Jwts.parser().verifyWith(JwtHelper.KEY).build();
                // Parse the signed token and extract the claim (payload)
                Claims claims = parser.parseSignedClaims(token).getPayload();

                // Subject contains the user identifier (in this case: email)
                String email = claims.getSubject();
                // "role" claim contains the role we stored when creating the token
                String role = claims.get("role", String.class);

                // Wrap the email + role in the SecurityUser object
                user = new SecurityUser(email, role);

                // For debugging in the console so we see who is logged in
                System.out.println("Valid JWT from: " + email);

            } catch (JwtException e) {
                // if anything goes wrong (expired token, wrong signature, etc.), it's treated as invalid
                System.out.println("Invalid JWT");
            }
        }

        // Determine path and method for access control
        String path = requestCtx.getUriInfo().getPath();   // e.g. "bookings", "facilities/1"
        String method = requestCtx.getMethod();            // GET, POST, PUT, DELETE

        // Public endpoints: no login required
        // Registrations POST (registration) is public, but DELETE requires auth
        boolean isPublic =
                path.startsWith("login") ||
                        path.startsWith("verify") ||  // Email verification moet zonder login kunnen
                        (path.startsWith("registrations") && "POST".equals(method)) ||  // Only registration is public
                        path.startsWith("contactPage") ||
                        path.startsWith("breads") ||
                        path.startsWith("bookings") ||
                        path.startsWith("home") ||
                        path.startsWith("products") ||
                        path.startsWith("reservations") ||
                        path.startsWith("themes") ||                           // 👈 deze erbij
                        path.startsWith("camping-info") ||
                        (path.startsWith("facilities") && "GET".equals(method));

        if (!isPublic) {
            // For all other endpoints, user must be logged in
            if (user == null) {
                requestCtx.abortWith(
                        jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                                .entity("Login vereist")
                                .build()
                );
                return;
            }

            // Admin-only endpoints: dashboard + wijzigen van facilities
            boolean adminOnly =
                    path.startsWith("dashboard") ||
                            (path.startsWith("facilities") && !"GET".equals(method));

            if (adminOnly && !"admin".equals(user.getRole())) {
                requestCtx.abortWith(
                        jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.FORBIDDEN)
                                .entity("Geen toegang")
                                .build()
                );
                return;
            }

            // bookings: hier is alleen "ingelogd zijn" genoeg (user of admin)
            // => geen extra check nodig omdat user hierboven al gecontroleerd is
        }

        // Attack SecurityUser (or null) to the request using MySecurityContext
        // Resources can ask:  who is the user? what is their role? etc.
        requestCtx.setSecurityContext(new MySecurityContext(user, requestCtx.getUriInfo().getRequestUri().getScheme()));
    }
}
