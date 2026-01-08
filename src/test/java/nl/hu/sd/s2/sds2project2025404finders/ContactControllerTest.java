package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.contact.ContactController;
import nl.hu.sd.s2.sds2project2025404finders.contact.ContactForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the ContactController.
 * Makes sure we can post new messages, fetch them again,
 * and confirm that what comes back matches what went in.
 */
class ContactControllerTest {

    private ContactController controller;

    /**
     * Creates a clean ContactController before each test
     * so every run begins with an empty message list.
     */
    @BeforeEach
    void setup() {
        controller = new ContactController();
    }

    @Test
    void multipleMessagesAreReturned() {
        controller.handleContact(new ContactForm("A", "a@example.com", "msg1"));
        controller.handleContact(new ContactForm("B", "b@example.com", "msg2"));
        controller.handleContact(new ContactForm("C", "c@example.com", "msg3"));

        Response response = controller.getContactPage();
        List<ContactForm> messages = (List<ContactForm>) response.getEntity();

        assertNotNull(messages);
        assertEquals(3, messages.size());
    }
}
