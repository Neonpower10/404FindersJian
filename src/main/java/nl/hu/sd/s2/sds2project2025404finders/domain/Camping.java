package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a camping site that can have multiple bookings.
 * Contains information about the camping ID, name, and associated bookings.
 */
public class Camping {
    private int campingId;
    private String campingName;
    private List<Booking> bookings;

    /** Default constructor */
    public Camping() {}

    public Camping(int campingId, String campingName, List<Booking> bookings) {
        this.campingId = campingId;
        this.campingName = campingName;
        this.bookings = bookings;
    }

    /** Generates a sample Camping object with demo bookings */
    static public Camping getCamping() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(
                1,
                "Meneer",
                "Jeremy",
                "Trimoredjo",
                "jeremy@example.com",
                "0612345678",
                "Voorbeeldstraat",
                "1",
                "1234 AB",
                "Stad",
                "Nederland",
                "Caravan",
                "25-06-2025",
                "01-08-2025",
                "03-05-2025",
                4
        ));
        bookings.add(new Booking(
                2,
                "Meneer",
                "John",
                "Doe",
                "john@example.com",
                "0612345679",
                "Straatweg",
                "5",
                "5678 CD",
                "Plaats",
                "Nederland",
                "Tent",
                "10-12-2025",
                "12-01-2026",
                "31-10-2025",
                2
        ));
        bookings.add(new Booking(
                3,
                "Mevrouw",
                "Jane",
                "Doe",
                "jane@example.com",
                "0612345680",
                "Dorpsstraat",
                "10",
                "9012 EF",
                "Dorp",
                "Nederland",
                "Huisje",
                "04-04-2026",
                "05-05-2026",
                "03-03-2026",
                5
        ));
        return new Camping(1, "Camping De Natuur", bookings);
    }

    // --- Getters & Setters ---
    public int getCampingId() { return campingId; }
    public void setCampingId(int campingId) { this.campingId = campingId; }

    public String getCampingName() { return campingName; }
    public void setCampingName(String campingName) { this.campingName = campingName; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
