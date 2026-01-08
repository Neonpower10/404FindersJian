package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.database.DatabaseConnection;
import nl.hu.sd.s2.sds2project2025404finders.database.ThemeDao;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThemeDaoTest {

    private ThemeDao dao;

    @BeforeEach
    void setUp() {
        dao = new ThemeDao();
        cleanupTestTheme("testtheme");
        cleanupTestTheme("updatedtheme");
    }

    @AfterEach
    void tearDown() {
        cleanupTestTheme("testtheme");
        cleanupTestTheme("updatedtheme");
    }

    // ---------- helpers ----------

    private void cleanupTestTheme(String name) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement("DELETE FROM themes WHERE name = ? AND is_default = false")) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception ignored) {
        }
    }

    // ---------- tests ----------

    @Test
    void saveTheme_PersistsThemeAndVariables() {
        dao.saveTheme("testtheme", Map.of(
                "--bg", "#ffffff",
                "--text", "#000000"
        ));

        Map<String, Map<String, String>> themes = dao.getAllThemes();

        assertTrue(themes.containsKey("testtheme"));
        assertEquals("#ffffff", themes.get("testtheme").get("--bg"));
        assertEquals("#000000", themes.get("testtheme").get("--text"));
    }

    @Test
    void updateTheme_ChangesNameAndVariables() {
        dao.saveTheme("testtheme", Map.of("--bg", "#fff"));

        dao.updateTheme(
                "testtheme",
                "updatedtheme",
                Map.of("--bg", "#000", "--text", "#111")
        );

        Map<String, Map<String, String>> themes = dao.getAllThemes();

        assertFalse(themes.containsKey("testtheme"));
        assertTrue(themes.containsKey("updatedtheme"));
        assertEquals("#000", themes.get("updatedtheme").get("--bg"));
        assertEquals("#111", themes.get("updatedtheme").get("--text"));
    }

    @Test
    void updateTheme_NonExistingTheme_ThrowsException() {
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                dao.updateTheme("idontexist", "newname", Map.of("--bg", "#000"))
        );

        assertTrue(ex.getMessage().contains("Theme not found"));
    }

    @Test
    void deleteTheme_RemovesTheme() {
        dao.saveTheme("testtheme", Map.of("--bg", "#fff"));

        dao.deleteTheme("testtheme");

        Map<String, Map<String, String>> themes = dao.getAllThemes();
        assertFalse(themes.containsKey("testtheme"));
    }

    @Test
    void deleteTheme_DoesNotDeleteDefaultThemes() {
        dao.deleteTheme("default");

        Map<String, Map<String, String>> themes = dao.getAllThemes();
        assertTrue(themes.containsKey("default"));
    }

    @Test
    void getAllThemes_ReturnsDefaultsAtLeast() {
        Map<String, Map<String, String>> themes = dao.getAllThemes();

        assertNotNull(themes);
        assertTrue(themes.containsKey("default"));
        assertTrue(themes.containsKey("forest - standard"));
    }
}
