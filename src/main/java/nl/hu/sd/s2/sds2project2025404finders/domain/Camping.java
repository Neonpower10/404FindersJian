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

    /**
     * Default constructor for creating an empty Camping object.
     */
    public Camping() {}

    /**
     * Creates a new Camping object with the specified details.
     *
     * @param campingId   Unique identifier for the camping site.
     * @param campingName Name of the camping site.
     * @param bookings    List of bookings associated with this camping.
     */
    public Camping(int campingId, String campingName, List<Booking> bookings) {
        this.campingId = campingId;
        this.campingName = campingName;
        this.bookings = bookings;
    }

    /**
     * Generates a sample Camping object with demo bookings.
     *
     * @return A Camping object with a few pre-defined bookings.
     */
    static public Camping getCamping() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1, "Jeremy", "Trimoredjo", "Caravan", "25-06-2025", "01-08-2025", 4));
        bookings.add(new Booking(2, "John", "Doe", "Tent", "10-12-2025", "12-01-2026", 2));
        bookings.add(new Booking(3, "Jane", "Doe", "Huisje", "04-04-2026", "05-05-2026", 5));
        return new Camping(1, "Camping De Natuur", bookings);
    }

    /**
     * Returns the unique ID of the camping site.
     *
     * @return campingId
     */
    public int getCampingId() {
        return campingId;
    }

    /**
     * Sets the unique ID of the camping site.
     *
     * @param campingId Unique identifier for the camping site.
     */
    public void setCampingId(int campingId) {
        this.campingId = campingId;
    }

    /**
     * Returns the name of the camping site.
     *
     * @return campingName
     */
    public String getCampingName() {
        return campingName;
    }

    /**
     * Sets the name of the camping site.
     *
     * @param campingName Name of the camping site.
     */
    public void setCampingName(String campingName) {
        this.campingName = campingName;
    }

    /**
     * Returns the list of bookings associated with this camping site.
     *
     * @return List of Booking objects.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings associated with this camping site.
     *
     * @param bookings List of Booking objects.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
