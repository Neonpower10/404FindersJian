package nl.hu.sd.s2.sds2project2025404finders.database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ThemeDao {

    /**
     * Returns all themes including their variables.
     * Output format:
     * Map<themeName, Map<cssVariable, value>>
     */
    public Map<String, Map<String, String>> getAllThemes() {
        Map<String, Map<String, String>> themes = new HashMap<>();

        String sql = """
            SELECT t.name, v.variable_name, v.variable_value
            FROM themes t
            JOIN theme_variables v ON t.theme_id = v.theme_id
            ORDER BY t.name
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String themeName = rs.getString("name");
                String varName = rs.getString("variable_name");
                String varValue = rs.getString("variable_value");

                themes
                        .computeIfAbsent(themeName, k -> new HashMap<>())
                        .put(varName, varValue);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load themes", e);
        }

        return themes;
    }

    /**
     * Saves a new custom theme.
     */
    public void saveTheme(String name, Map<String, String> variables) {
        String insertThemeSql =
                "INSERT INTO themes (name, is_default) VALUES (?, false) RETURNING theme_id";

        String insertVariablesSql =
                "INSERT INTO theme_variables (theme_id, variable_name, variable_value) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            int themeId;

            try (PreparedStatement ps = con.prepareStatement(insertThemeSql)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                rs.next();
                themeId = rs.getInt("theme_id");
            }

            try (PreparedStatement ps = con.prepareStatement(insertVariablesSql)) {
                for (Map.Entry<String, String> entry : variables.entrySet()) {
                    ps.setInt(1, themeId);
                    ps.setString(2, entry.getKey());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to save theme", e);
        }
    }

    /**
     * Updates an existing theme (rename + variables).
     */
    public void updateTheme(String oldName, String newName, Map<String, String> variables) {
        String selectIdSql = "SELECT theme_id FROM themes WHERE name = ?";
        String updateNameSql = "UPDATE themes SET name = ? WHERE theme_id = ?";
        String deleteVarsSql = "DELETE FROM theme_variables WHERE theme_id = ?";
        String insertVarsSql =
                "INSERT INTO theme_variables (theme_id, variable_name, variable_value) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            int themeId;

            try (PreparedStatement ps = con.prepareStatement(selectIdSql)) {
                ps.setString(1, oldName);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException("Theme not found: " + oldName);
                }
                themeId = rs.getInt("theme_id");
            }

            try (PreparedStatement ps = con.prepareStatement(updateNameSql)) {
                ps.setString(1, newName);
                ps.setInt(2, themeId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(deleteVarsSql)) {
                ps.setInt(1, themeId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(insertVarsSql)) {
                for (Map.Entry<String, String> entry : variables.entrySet()) {
                    ps.setInt(1, themeId);
                    ps.setString(2, entry.getKey());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to update theme", e);
        }
    }

    /**
     * Deletes a custom theme.
     */
    public void deleteTheme(String name) {
        String sql = "DELETE FROM themes WHERE name = ? AND is_default = false";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to delete theme", e);
        }
    }
}
