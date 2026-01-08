package nl.hu.sd.s2.sds2project2025404finders.dao;

import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO {
    boolean save(Booking inBooking) throws SQLException;
    boolean update(Booking inBooking) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Booking> findAll() throws SQLException;
    Booking findById(int id) throws SQLException;
}
