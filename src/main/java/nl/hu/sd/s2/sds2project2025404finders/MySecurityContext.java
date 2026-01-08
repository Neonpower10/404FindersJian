package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

// Custom SecurityContext used by Jersey to know "who is the user" and "what is their role"
public class MySecurityContext implements SecurityContext {

    // THe authenticated user (or null if guest)
    private final SecurityUser user;
    // the scheme of the current request, https or http
    private final String scheme;

    // Construct the context with a user and the request scheme
    public MySecurityContext(SecurityUser user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    // Returns the current user as a Principal (may be null if not logged in)
    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    // Returns true if the current user has the given role
    @Override
    public boolean isUserInRole(String role) {
        // IF there is no user, they have no rule
        if (user == null) return false;
        // Check if the requested role matches the user's role
        return role.equals(user.getRole());
    }

    // Returns true if the request is sent over HTTPS
    @Override
    public boolean isSecure() {
        return "https".equalsIgnoreCase(scheme);
    }

    // Returns the authentication scheme used
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}