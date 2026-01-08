package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Login;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;
import nl.hu.sd.s2.sds2project2025404finders.loginandregistration.LoginResource;
import nl.hu.sd.s2.sds2project2025404finders.loginandregistration.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the LoginResource.
 * - login (admin & regular user)
 * - Incorrect passwords / users / empty fields
 */
class LoginResourceTest {

    private LoginResource loginResource;

    @BeforeEach
    void setUp() {
        loginResource = new LoginResource();

        if (RegistrationRepository.findByEmail("admin@camping.nl") == null) {
            Registration admin = new Registration();
            admin.setName("Admin");
            admin.setSurname("PO");
            admin.setEmail("admin@camping.nl");
            admin.setPassword("Admin123!");
            RegistrationRepository.add(admin);
        }

        // create normal user for the user login test
        if (RegistrationRepository.findByEmail("user@example.com") == null) {
            Registration user = new Registration();
            user.setName("Normal");
            user.setSurname("User");
            user.setEmail("user@example.com");
            user.setPassword("User123!");
            RegistrationRepository.add(user);
        }
    }

    @Test
    void loginSucceedsForAdminWithCorrectCredentials() {
        Login req = new Login();
        req.email = "admin@camping.nl";
        req.password = "Admin123!";

        Response response = loginResource.login(req);
        assertEquals(200, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(true, body.get("ok"));
        assertEquals("admin", body.get("role"));
        assertEquals("admin@camping.nl", body.get("email"));
        assertNotNull(body.get("token"));
    }

    @Test
    void loginSucceedsForUserWithCorrectCredentials() {
        Login req = new Login();
        req.email = "user@example.com";
        req.password = "User123!";

        Response response = loginResource.login(req);
        assertEquals(200, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(true, body.get("ok"));
        assertEquals("user", body.get("role"));
        assertEquals("user@example.com", body.get("email"));
        assertNotNull(body.get("token"));
    }

    @Test
    void loginFailsWithWrongPassword() {
        Login req = new Login();
        req.email = "admin@camping.nl";
        req.password = "VerkeerdWachtwoord";

        Response response = loginResource.login(req);
        assertEquals(401, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(false, body.get("ok"));
        assertEquals("Onjuiste inloggegevens.", body.get("message"));
    }

    @Test
    void loginFailsForUnknownEmail() {
        Login req = new Login();
        req.email = "bestaatniet@example.com";
        req.password = "Whatever123!";

        Response response = loginResource.login(req);
        assertEquals(401, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(false, body.get("ok"));
        assertEquals("Onjuiste inloggegevens.", body.get("message"));
    }

    @Test
    void loginFailsWhenFieldsMissing() {
        Login req = new Login();
        req.email = "   ";   // empty after trim
        req.password = "";   // empty

        Response response = loginResource.login(req);
        assertEquals(400, response.getStatus());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getEntity();

        assertNotNull(body);
        assertEquals(false, body.get("ok"));
        assertEquals("Vul e-mailadres en wachtwoord in.", body.get("message"));
    }
}
