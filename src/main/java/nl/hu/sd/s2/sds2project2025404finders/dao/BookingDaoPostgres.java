package nl.hu.sd.s2.sds2project2025404finders.dao;

import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoPostgres implements BookingDAO{

    // Container for the connection
    private Connection connection = null;

    // Constructor that takes a connection
    // It does NOT create its own connection
    public BookingDaoPostgres(Connection inConnection)
            throws SQLException {

        this.connection = inConnection;
    }
    @Override
    public boolean save(Booking inBooking) throws SQLException {
        //TODO make save happen
        return false;
    }

    @Override
    public boolean update(Booking inBooking) throws SQLException {
        //TODO make update happen
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String q = "DELETE FROM booking WHERE bookingid = ?";
        try (PreparedStatement ps = connection.prepareStatement(q)) {
            ps.setInt(1, id);
            int rows =  ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Booking with id: " + id + " has been deleted");
                return true;
            } else  {
                System.out.println("Booking with id: " + id + " can't be found");
                return false;
            }
        }
    }

    @Override
    public List<Booking> findAll() throws SQLException {
        String q = "SELECT * FROM booking";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(q)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("bookingid"));
                booking.setGender(rs.getString("gender"));
                booking.setPersonFirstName(rs.getString("firstname"));
                booking.setPersonLastName(rs.getString("lastname"));
                booking.setEmail(rs.getString("email"));
                booking.setPhoneNumber(rs.getString("phonenumber"));
                booking.setStreetName(rs.getString("streetname"));
                booking.setHouseNumber(rs.getString("housenumber"));
                booking.setPostcode(rs.getString("postcode"));
                booking.setPlace(rs.getString("place"));
                booking.setCountry(rs.getString("country"));
                booking.setCampPlace(rs.getString("campingid"));
                booking.setStartDate(rs.getString("startdate"));
                booking.setEndDate(rs.getString("enddate"));
                booking.setAmountPersons(rs.getInt("peopleamount"));

                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public Booking findById(int id) throws SQLException {
        String q = "SELECT * FROM booking WHERE bookingid = ?";
        try (PreparedStatement pst = connection.prepareStatement(q)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("bookingid"));
                booking.setGender(rs.getString("gender"));
                booking.setPersonFirstName(rs.getString("firstname"));
                booking.setPersonLastName(rs.getString("lastname"));
                booking.setEmail(rs.getString("email"));
                booking.setPhoneNumber(rs.getString("phonenumber"));
                booking.setStreetName(rs.getString("streetname"));
                booking.setHouseNumber(rs.getString("housenumber"));
                booking.setPostcode(rs.getString("postcode"));
                booking.setPlace(rs.getString("place"));
                booking.setCountry(rs.getString("country"));
                booking.setCampPlace(rs.getString("campingid"));
                booking.setStartDate(rs.getString("startdate"));
                booking.setEndDate(rs.getString("enddate"));
                booking.setAmountPersons(rs.getInt("peopleamount"));

                return booking;
            }
        }
        return null;
    }
}