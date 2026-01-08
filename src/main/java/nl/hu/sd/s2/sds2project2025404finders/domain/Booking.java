package nl.hu.sd.s2.sds2project2025404finders.domain;

/**
 * Represents a booking made by a person at a camping site.
 * Contains information about the booking ID, guest name, camp place, booking dates, and number of persons.
 */
public class Booking {
    private int bookingId;
    private String personFirstName;
    private String personLastName;
    private String campPlace;
    private String startDate;
    private String endDate;
    private int amountPersons;

    /**
     * Default constructor for creating an empty Booking object.
     */
    public Booking() {}

    /**
     * Creates a new Booking with all required fields.
     *
     * @param bookingId       Unique identifier for the booking.
     * @param personFirstName First name of the person making the booking.
     * @param personLastName  Last name of the person making the booking.
     * @param campPlace       Type of camp place (e.g., Tent, Caravan, House).
     * @param startDate       Start date of the booking in DD-MM-YYYY format.
     * @param endDate         End date of the booking in DD-MM-YYYY format.
     * @param amountPersons   Number of persons included in the booking.
     */
    public Booking(int bookingId, String personFirstName, String personLastName, String campPlace, String startDate, String endDate, int amountPersons) {
        this.bookingId = bookingId;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.campPlace = campPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountPersons = amountPersons;
    }

    /**
     * Returns the unique ID of the booking.
     *
     * @return bookingId
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     *
     * @param bookingId Unique identifier for the booking.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Returns the first name of the person who made the booking.
     *
     * @return personFirstName
     */
    public String getPersonFirstName() {
        return personFirstName;
    }

    /**
     * Sets the first name of the person making the booking.
     *
     * @param personFirstName First name of the guest.
     */
    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    /**
     * Returns the last name of the person who made the booking.
     *
     * @return personLastName
     */
    public String getPersonLastName() {
        return personLastName;
    }

    /**
     * Sets the last name of the person making the booking.
     *
     * @param personLastName Last name of the guest.
     */
    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    /**
     * Returns the type of camp place for the booking.
     *
     * @return campPlace
     */
    public String getCampPlace() {
        return campPlace;
    }

    /**
     * Sets the type of camp place for the booking.
     *
     * @param campPlace Type of camp place (e.g., Tent, Caravan, House).
     */
    public void setCampPlace(String campPlace) {
        this.campPlace = campPlace;
    }

    /**
     * Returns the start date of the booking.
     *
     * @return startDate in DD-MM-YYYY format.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the booking.
     *
     * @param startDate Start date in DD-MM-YYYY format.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the end date of the booking.
     *
     * @return endDate in DD-MM-YYYY format.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the booking.
     *
     * @param endDate End date in DD-MM-YYYY format.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the number of persons included in the booking.
     *
     * @return amountPersons
     */
    public int getAmountPersons() {
        return amountPersons;
    }

    /**
     * Sets the number of persons included in the booking.
     *
     * @param amountPersons Number of guests.
     */
    public void setAmountPersons(int amountPersons) {
        this.amountPersons = amountPersons;
    }

    /**
     * Returns a string representation of the booking, useful for debugging.
     *
     * @return String with all booking details.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", person='" + personFirstName + " " + personLastName + '\'' +
                ", campPlace='" + campPlace + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", amountPersons=" + amountPersons +
                '}';
    }
}
