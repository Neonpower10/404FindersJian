package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.theme.ThemeResource;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThemeResourceTest {

    private ThemeResource resource;
    private File tempFile;

    @BeforeEach
    void setUp() throws Exception {
        resource = new ThemeResource();

        // Create isolated temp file
        tempFile = File.createTempFile("testThemes", ".json");
        tempFile.deleteOnExit();

        // Reflect into private static final field CUSTOM_THEMES_FILE
        Field f = ThemeResource.class.getDeclaredField("CUSTOM_THEMES_FILE");
        f.setAccessible(true);
        f.set(null, tempFile);
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testGetThemes_ReturnsDefaultThemes() {
        Map<String, Map<String, String>> themes = resource.getThemes();

        assertNotNull(themes);
        assertTrue(themes.containsKey("default"));
        assertTrue(themes.containsKey("forest - standard"));
        assertTrue(themes.containsKey("sunset - standard"));
        assertTrue(themes.containsKey("midnight - standard"));
        assertTrue(themes.containsKey("ocean - standard"));

        // Verify base structure
        Map<String, String> defaultTheme = themes.get("default");
        assertEquals("#f8fafc", defaultTheme.get("--bg"));
        assertEquals("#ffffff", defaultTheme.get("--accent-text"));
    }

    @Test
    void testGetThemes_IncludesCustomThemes() throws IOException {
        // Prepare custom theme in the temp file
        Files.writeString(tempFile.toPath(),
                """
                {
                    "mycustom": { "--bg": "#000000", "--text": "#ffffff" }
                }
                """
        );

        Map<String, Map<String, String>> themes = resource.getThemes();
        assertTrue(themes.containsKey("mycustom"));
    }

    @Test
    void testSaveCustomTheme_Success() throws IOException {
        Map<String, Object> data = Map.of(
                "name", "TestTheme",
                "variables", Map.of("--bg", "#fff", "--text", "#000")
        );

        Response response = resource.saveCustomTheme(data);
        assertEquals(200, response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertTrue(json.contains("testtheme")); // lowercase enforced
    }

    @Test
    void testSaveCustomTheme_InvalidFormat_NameMissing() {
        Map<String, Object> data = Map.of("variables", Map.of("--bg", "#fff"));
        Response response = resource.saveCustomTheme(data);
        assertEquals(400, response.getStatus());
    }

    @Test
    void testSaveCustomTheme_InvalidFormat_VariablesMissing() {
        Map<String, Object> data = Map.of("name", "incomplete");
        Response response = resource.saveCustomTheme(data);
        assertEquals(400, response.getStatus());
    }

    @Test
    void testSaveCustomTheme_OverwritesExisting() throws IOException {
        // first save
        resource.saveCustomTheme(Map.of(
                "name", "duplicate",
                "variables", Map.of("--bg", "#fff")
        ));

        // overwrite
        resource.saveCustomTheme(Map.of(
                "name", "duplicate",
                "variables", Map.of("--bg", "#000")
        ));

        String json = Files.readString(tempFile.toPath());
        assertTrue(json.contains("\"--bg\" : \"#000\""));
    }

    @Test
    void testUpdateTheme_Success() throws IOException {
        resource.saveCustomTheme(Map.of(
                "name", "oldtheme",
                "variables", Map.of("--bg", "#fff")
        ));

        Response response = resource.updateTheme("oldtheme", Map.of(
                "newName", "UpdatedTheme",
                "variables", Map.of("--bg", "#000")
        ));

        assertEquals(200, response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertTrue(json.contains("updatedtheme"));
        assertFalse(json.contains("oldtheme"));
    }

    @Test
    void testUpdateTheme_NotFound() {
        Response r = resource.updateTheme("idontexist", Map.of(
                "newName", "newer",
                "variables", Map.of("--bg", "#000")
        ));
        assertEquals(404, r.getStatus());
    }

    @Test
    void testUpdateTheme_InvalidFormat() throws IOException {
        resource.saveCustomTheme(Map.of(
                "name", "something",
                "variables", Map.of("--bg", "#fff")
        ));

        Response r = resource.updateTheme("something", Map.of(
                "newName", "",
                "variables", Map.of()
        ));

        assertEquals(400, r.getStatus());
    }

    @Test
    void testDeleteTheme_Success() throws IOException {
        resource.saveCustomTheme(Map.of(
                "name", "deleteme",
                "variables", Map.of("--bg", "#fff")
        ));

        Response response = resource.deleteTheme("deleteme");
        assertEquals(200, response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertFalse(json.contains("deleteme"));
    }

    @Test
    void testDeleteTheme_NotFound() {
        Response response = resource.deleteTheme("nope");
        assertEquals(404, response.getStatus());
    }
}
