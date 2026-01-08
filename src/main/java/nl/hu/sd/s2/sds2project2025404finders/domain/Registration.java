package nl.hu.sd.s2.sds2project2025404finders.domain;

public class Registration {
    private int registrationId;
    private String name;
    private String surname;
    private String password;
    private String email;

    // Standard everyone is 'user' (kampeerder)
    private String role = "user";

    // Of een account al via e-mail bevestigd is
    private boolean verified = false;
    private String verificationToken;

    public Registration() {
    }

    public Registration(int registrationId, String name, String surname, String password, String email) {
        this.registrationId = registrationId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        // role stays "user"
    }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }

    // getters/setters for3 role
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='***'" +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
