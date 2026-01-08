package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.theme.ThemeResource;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThemeResourceTest {

    private ThemeResource resource;

    @BeforeEach
    void setUp() {
        resource = new ThemeResource();
    }

    // ---------------- GET ----------------

    @Test
    void testGetThemes_ReturnsThemesFromDatabase() {
        Map<String, Map<String, String>> themes = resource.getThemes();

        assertNotNull(themes);
        assertFalse(themes.isEmpty());

        // Default themes must exist
        assertTrue(themes.containsKey("default"));
        assertTrue(themes.containsKey("forest - standard"));
        assertTrue(themes.containsKey("sunset - standard"));
        assertTrue(themes.containsKey("midnight - standard"));
        assertTrue(themes.containsKey("ocean - standard"));
    }

    // ---------------- POST ----------------

    @Test
    void testSaveTheme_Success() {
        Response response = resource.saveTheme(Map.of(
                "name", "TestTheme",
                "variables", Map.of("--bg", "#fff", "--text", "#000")
        ));

        assertEquals(200, response.getStatus());

        Map<String, Map<String, String>> themes = resource.getThemes();
        assertTrue(themes.containsKey("testtheme"));
        assertEquals("#fff", themes.get("testtheme").get("--bg"));
    }

    @Test
    void testSaveTheme_InvalidFormat_NameMissing() {
        Response response = resource.saveTheme(Map.of(
                "variables", Map.of("--bg", "#fff")
        ));

        assertEquals(400, response.getStatus());
    }

    @Test
    void testSaveTheme_InvalidFormat_VariablesMissing() {
        Response response = resource.saveTheme(Map.of(
                "name", "incomplete"
        ));

        assertEquals(400, response.getStatus());
    }

    // ---------------- PUT ----------------

    @Test
    void testUpdateTheme_Success() {
        resource.saveTheme(Map.of(
                "name", "oldtheme",
                "variables", Map.of("--bg", "#fff")
        ));

        Response response = resource.updateTheme("oldtheme", Map.of(
                "newName", "updatedtheme",
                "variables", Map.of("--bg", "#000")
        ));

        assertEquals(200, response.getStatus());

        Map<String, Map<String, String>> themes = resource.getThemes();
        assertTrue(themes.containsKey("updatedtheme"));
        assertFalse(themes.containsKey("oldtheme"));
        assertEquals("#000", themes.get("updatedtheme").get("--bg"));
    }

    @Test
    void testUpdateTheme_InvalidFormat() {
        Response response = resource.updateTheme("something", Map.of(
                "newName", "",
                "variables", Map.of()
        ));

        assertEquals(400, response.getStatus());
    }

    // ---------------- DELETE ----------------

    @Test
    void testDeleteTheme_Success() {
        resource.saveTheme(Map.of(
                "name", "deleteme",
                "variables", Map.of("--bg", "#fff")
        ));

        Response response = resource.deleteTheme("deleteme");
        assertEquals(200, response.getStatus());

        Map<String, Map<String, String>> themes = resource.getThemes();
        assertFalse(themes.containsKey("deleteme"));
    }
}
