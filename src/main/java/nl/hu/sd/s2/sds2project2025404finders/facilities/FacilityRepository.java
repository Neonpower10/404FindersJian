package nl.hu.sd.s2.sds2project2025404finders.facilities;

import nl.hu.sd.s2.sds2project2025404finders.DatabaseConnection;
import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityRepository {

    public List<Facility> getAll() {
        List<Facility> facilities = new ArrayList<>();

        String sql = "SELECT * FROM facility";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                facilities.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facilities;
    }

    public Facility findById(int id) {
        String sql = "SELECT * FROM facility WHERE facilityid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Facility add(Facility facility) {
        String sql = """
        INSERT INTO facility (facilityname, facilitydescription, image, status)
        VALUES (?, ?, ?, ?)
        RETURNING facilityid
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facility.getName());
            ps.setString(2, facility.getDescription());
            ps.setString(3, facility.getImage());
            ps.setString(4, facility.getStatus());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                facility.setId(rs.getInt("facilityid"));
            }

            return facility;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Facility update(int id, Facility updated) {
        String sql = """
            UPDATE facility
            SET facilityname = ?, facilitydescription = ?, image = ?, status = ?
            WHERE facilityid = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, updated.getName());
            ps.setString(2, updated.getDescription());
            ps.setString(3, updated.getImage());
            ps.setString(4, updated.getStatus());
            ps.setInt(5, id);

            if (ps.executeUpdate() > 0) {
                updated.setId(id);
                return updated;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM facility WHERE facilityid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Facility mapRow(ResultSet rs) throws SQLException {
        return new Facility(
                rs.getInt("facilityid"),
                rs.getString("facilityname"),
                rs.getString("facilitydescription"),
                rs.getString("image"),
                rs.getString("status")
        );
    }
}
