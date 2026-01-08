package nl.hu.sd.s2.sds2project2025404finders;

public class ContactForm {
    private String name;
    private String email;
    private String message;

    public ContactForm() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ContactForm{name='" + name + "', email='" + email + "', message='" + message + "'}";
    }
}
