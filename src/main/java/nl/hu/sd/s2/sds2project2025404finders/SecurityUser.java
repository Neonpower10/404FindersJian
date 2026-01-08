package nl.hu.sd.s2.sds2project2025404finders;

import java.security.Principal;

// SImple user object used inside the security system (email + role)
public class SecurityUser implements Principal {

    // unique identifier of the user, here we use email
    private final String email;
    // Role of the user.
    private final String role;

    // Creates a security user with email and role
    public SecurityUser(String email, String role) {
        this.email = email;
        this.role = role;
    }

    // getName() is required by Principal and returns the main identifier
    @Override
    public String getName() {
        return email;
    }
    // a getter to retrieve the role later in MySecurityContext
    public String getRole() {
        return role;
    }
}