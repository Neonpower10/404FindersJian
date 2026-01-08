package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;
import nl.hu.sd.s2.sds2project2025404finders.loginandregistration.RegistrationRepository;
import nl.hu.sd.s2.sds2project2025404finders.loginandregistration.RegistrationResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for RegistrationResource:
 * - successful registration
 * - duplicate email
 * - missing fields
 */
class RegistrationResourceTest {

    private RegistrationResource registrationResource;

    @BeforeEach
    void setUp() {
        registrationResource = new RegistrationResource();

        // ensure that admin always exists (for duplicate email test)
        if (RegistrationRepository.findByEmail("admin@camping.nl") == null) {
            Registration admin = new Registration();
            admin.setName("Admin");
            admin.setSurname("PO");
            admin.setEmail("admin@camping.nl");
            admin.setPassword("Admin123!");
            admin.setVerified(true);
            RegistrationRepository.add(admin);
        }
    }

    @Test
    void registrationSucceedsWithValidData() {
        String email = "newuser@example.com";

        // If this email already exists from an earlier test run, choose another one
        if (RegistrationRepository.findByEmail(email) != null) {
            email = "newuser2@example.com";
        }

        Registration reg = new Registration();
        reg.setName("New");
        reg.setSurname("User");
        reg.setEmail(email);
        reg.setPassword("Secret123!");

        Response response = registrationResource.addRegistration(reg);
        assertEquals(201, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(true, body.get("ok"));
        assertNotNull(body.get("id"));

        assertTrue(body.containsKey("message"));
        assertNotNull(body.get("message"));

        // also check that it's actually in the repository
        Registration stored = RegistrationRepository.findByEmail(email);
        assertNotNull(stored);
        assertEquals("New", stored.getName());
        assertEquals("User", stored.getSurname());
    }

    @Test
    void registrationFailsWhenEmailAlreadyExists() {
        // admin@camping.nl already exists from setUp()
        Registration reg = new Registration();
        reg.setName("Dup");
        reg.setSurname("User");
        reg.setEmail("admin@camping.nl");
        reg.setPassword("blabla123!");

        Response response = registrationResource.addRegistration(reg);
        assertEquals(409, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(false, body.get("ok"));

        // Just check presence of message
        assertTrue(body.containsKey("message"));
        assertNotNull(body.get("message"));
    }

    @Test
    void registrationFailsWhenRequiredFieldsMissing() {
        Registration reg = new Registration();
        // no name, no surname, no password
        reg.setEmail("incomplete@example.com");

        Response response = registrationResource.addRegistration(reg);
        assertEquals(400, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(false, body.get("ok"));

        assertTrue(body.containsKey("message"));
        assertNotNull(body.get("message"));
    }
}
