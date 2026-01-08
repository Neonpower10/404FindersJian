package nl.hu.sd.s2.sds2project2025404finders.domain;

/**
 * Represents a booking made by a person at a camping site.
 * Includes personal info, address, and booking details.
 */
public class Booking {
    // --- General ---
    private int bookingId;

    // --- Personal info ---
    private String gender;
    private String personFirstName;
    private String personLastName;
    private String email;
    private String phoneNumber;

    // --- Address info ---
    private String streetName;
    private String houseNumber;
    private String postcode;
    private String place;
    private String country;

    // --- Booking info ---
    private String campEq;
    private String startDate;
    private String endDate;
    private String creationDate;
    private int amountPersons;

    /**
     * Default constructor for creating an empty Booking object.
     */
    public Booking() {
    }

    /**
     * Full constructor to create a Booking with all details.
     *
     * @param bookingId       Unique booking identifier
     * @param gender          Gender of the guest
     * @param personFirstName First name of the guest
     * @param personLastName  Last name of the guest
     * @param email           Email address
     * @param phoneNumber     Phone number
     * @param streetName      Street name
     * @param houseNumber     House number
     * @param postcode        Postal code
     * @param place           City / place
     * @param country         Country
     * @param campEq          Type of camping place (e.g., Tent, Caravan)
     * @param startDate       Booking start date (DD-MM-YYYY)
     * @param endDate         Booking end date (DD-MM-YYYY)
     * @param creationDate    Booking creation date (DD-MM-YYYY)
     * @param amountPersons   Number of persons
     */
    public Booking(int bookingId, String gender, String personFirstName, String personLastName,
                   String email, String phoneNumber, String streetName, String houseNumber,
                   String postcode, String place, String country, String campEq,
                   String startDate, String endDate, String creationDate, int amountPersons) {
        this.bookingId = bookingId;
        this.gender = gender;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.place = place;
        this.country = country;
        this.campEq = campEq;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.amountPersons = amountPersons;
    }

    // --- Getters & Setters ---
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCampEq() {
        return campEq;
    }

    public void setCampEq(String campEq) {
        this.campEq = campEq;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreationDate() {return creationDate;}

    public int getAmountPersons() {
        return amountPersons;
    }

    public void setAmountPersons(int amountPersons) {
        this.amountPersons = amountPersons;
    }

    /**
     * Returns a string representation of the booking. Useful for debugging.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", gender='" + gender + '\'' +
                ", person='" + personFirstName + " " + personLastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + streetName + " " + houseNumber + ", " + postcode + " " + place + ", " + country + '\'' +
                ", campEq='" + campEq + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", amountPersons=" + amountPersons +
                '}';
    }
}
