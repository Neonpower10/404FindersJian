package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.contact.ContactForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ContactForm class.
 * <p>
 * Just making sure getters, setters, and toString do what you'd expect.
 * </p>
 */
class ContactFormTest {

    /**
     * Checks that setting values actually updates the fields
     * and that the getters give back the right stuff.
     */
    @Test
    void gettersAndSettersWorkCorrectly() {
        ContactForm form = new ContactForm();

        form.setName("Chris");
        form.setEmail("chris@example.com");
        form.setMessage("Hello!");

        assertEquals("Chris", form.getName());
        assertEquals("chris@example.com", form.getEmail());
        assertEquals("Hello!", form.getMessage());
    }

    /**
     * Checks that the no-arg constructor gives us an object
     * with everything starting as null.
     */
    @Test
    void emptyConstructorCreatesNullFields() {
        ContactForm form = new ContactForm();

        assertNull(form.getName());
        assertNull(form.getEmail());
        assertNull(form.getMessage());
    }

    /**
     * Checks that toString returns a properly formatted string
     * with all the info in it.
     */
    @Test
    void toStringReturnsCorrectFormat() {
        ContactForm form = new ContactForm();
        form.setName("Anna");
        form.setEmail("anna@example.com");
        form.setMessage("Test message");

        String result = form.toString();

        assertTrue(result.contains("Anna"));
        assertTrue(result.contains("anna@example.com"));
        assertTrue(result.contains("Test message"));
        assertTrue(result.startsWith("ContactForm"));
    }
}