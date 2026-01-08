package nl.hu.sd.s2.sds2project2025404finders.domain;

/**
 * Represents a notification that is displayed on the admin dashboard.
 */
public class Notification {

    private int bookingId;          // The booking this notification is linked to
    private String firstName;       // Guest first name
    private String lastName;        // Guest last name
    private String message;         // Notification message (visible text)
    private boolean read;           // Whether this notification has been read

    /**
     * Full constructor for creating a Notification.
     *
     * @param bookingId Booking ID associated with the notification
     * @param firstName Guest first name
     * @param lastName Guest last name
     * @param message Notification text
     */
    public Notification(int bookingId, String firstName, String lastName, String message) {
        this.bookingId = bookingId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.read = false; // default to unread
    }

    // --- Getters & Setters ---
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns whether the notification has been read.
     */
    public boolean isRead() {
        return read;
    }

    /**
     * Marks the notification as read.
     */
    public void markAsRead() {
        this.read = true;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "bookingId=" + bookingId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                '}';
    }
}
