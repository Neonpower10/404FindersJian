package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BookingResource} class.
 * <p>
 * This test class verifies that the BookingResource behaves as expected,
 * including retrieving bookings, adding new ones, and validating their data.
 * </p>
 */
class BookingResourceTest {

    /**
     * Tests that the list of bookings returned by {@link BookingResource#bookings()}
     * is not empty, ensuring that initial mock data exists.
     */
    @Test
    void bookingsExist() {
        BookingResource bookingResource = new BookingResource();
        List<Booking> bookingList = bookingResource.bookings();

        assertFalse(bookingList.isEmpty());
    }

    /**
     * Tests that {@link BookingResource#bookings()} returns all existing bookings.
     * <p>
     * In this mock setup, the camping contains exactly three predefined bookings.
     * </p>
     */
    @Test
    void allBookingsGetReturned() {
        BookingResource bookingResource = new BookingResource();
        List<Booking> bookingList = bookingResource.bookings();

        assertEquals(3, bookingList.size());
    }

    /**
     * Tests that adding a new booking via {@link BookingResource#addBooking(Booking)}
     * returns a {@link Response} with HTTP status code 201 (Created)
     * and includes the created booking as the response entity.
     */
    @Test
    void bookingGetsPosted() {
        BookingResource bookingResource = new BookingResource();
        Booking newBooking = new Booking(
                0,
                "Meneer",
                "Chris. P",
                "Bacon",
                "chris@example.com",
                "0611111111",
                "Baconstraat",
                "8",
                "3456 CB",
                "Utrecht",
                "Nederland",
                "Tent",
                "10-09-2026",
                "15-09-2026",
                1
        );
        Response response = bookingResource.addBooking(newBooking);

        assertEquals(201, response.getStatus());

        Booking testBooking = (Booking) response.getEntity();

        assertNotNull(testBooking);
    }

    /**
     * Tests that all booking details remain correct after being added
     * through {@link BookingResource#addBooking(Booking)}.
     * <p>
     * This ensures that data integrity is preserved and that the booking object
     * returned by the API matches the data that was submitted.
     * </p>
     */
    @Test
    void bookingDetailsAreCorrect() {
        BookingResource bookingResource = new BookingResource();
        Booking newBooking = new Booking(
                0,
                "Meneer",
                "Michael",
                "Jackson",
                "heehee@example.com",
                "0611111111",
                "Jacksonstraat",
                "8",
                "3456 MJ",
                "Rotterdam",
                "Nederland",
                "Huisje",
                "25-06-2026",
                "25-07-2026",
                5
        );

        Response response = bookingResource.addBooking(newBooking);
        Booking testBooking = (Booking) response.getEntity();

        assertEquals("Meneer", testBooking.getGender());
        assertEquals("Michael", testBooking.getPersonFirstName());
        assertEquals("Jackson", testBooking.getPersonLastName());
        assertEquals("heehee@example.com", testBooking.getEmail());
        assertEquals("0611111111", testBooking.getPhoneNumber());
        assertEquals("Jacksonstraat", testBooking.getStreetName());
        assertEquals("8", testBooking.getHouseNumber());
        assertEquals("3456 MJ", testBooking.getPostcode());
        assertEquals("Rotterdam", testBooking.getPlace());
        assertEquals("Nederland", testBooking.getCountry());
        assertEquals("Huisje", testBooking.getCampPlace());
        assertEquals("25-06-2026", testBooking.getStartDate());
        assertEquals("25-07-2026", testBooking.getEndDate());
        assertEquals(5, testBooking.getAmountPersons());
    }
}
