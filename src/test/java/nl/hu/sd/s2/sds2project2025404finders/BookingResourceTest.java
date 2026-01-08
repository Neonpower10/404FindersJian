package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.booking.BookingResource;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BookingResource} class.
 */
class BookingResourceTest {

    private BookingResource bookingResource;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        bookingResource = new BookingResource();
        notificationService = NotificationService.getInstance();
        notificationService.clearNotifications(); // Reset notifications before each test
    }

    @Test
    void bookingsExist() {
        List<Booking> bookingList = bookingResource.bookings();
        assertFalse(bookingList.isEmpty());
    }

    @Test
    void allBookingsGetReturned() {
        List<Booking> bookingList = bookingResource.bookings();
        assertEquals(3, bookingList.size()); // Assuming initial mock bookings are 3
    }

    @Test
    void bookingGetsPosted() {
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
                "28-11-2025",
                1
        );

        Response response = bookingResource.addBooking(newBooking);
        assertEquals(201, response.getStatus());

        Booking testBooking = (Booking) response.getEntity();
        assertNotNull(testBooking);

        // --- Check notifications ---
        List<nl.hu.sd.s2.sds2project2025404finders.domain.Notification> notifications =
                notificationService.getAllNotifications();

        assertEquals(1, notifications.size());
        assertEquals(testBooking.getBookingId(), notifications.get(0).getBookingId());
        assertEquals("Nieuwe boeking aangemaakt", notifications.get(0).getMessage());
    }

    @Test
    void bookingDetailsAreCorrect() {
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
                "23-06-2025",
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
        assertEquals("23-06-2025", testBooking.getCreationDate());
        assertEquals(5, testBooking.getAmountPersons());

        // --- Check notification is also correct ---
        List<nl.hu.sd.s2.sds2project2025404finders.domain.Notification> notifications =
                notificationService.getAllNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Nieuwe boeking aangemaakt", notifications.get(0).getMessage());
        assertEquals(testBooking.getBookingId(), notifications.get(0).getBookingId());
    }
}
