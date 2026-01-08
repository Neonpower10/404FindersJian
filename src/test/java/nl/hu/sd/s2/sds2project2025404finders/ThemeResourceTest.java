package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
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

        // Create a temporary JSON file to avoid overwriting the real one
        tempFile = File.createTempFile("testThemes", ".json");
        tempFile.deleteOnExit();

        // Replace the private CUSTOM_THEMES_FILE field using reflection
        Field fileField = ThemeResource.class.getDeclaredField("CUSTOM_THEMES_FILE");
        fileField.setAccessible(true);
        fileField.set(null, tempFile);
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) tempFile.delete();
    }

    @Test
    void testGetThemes_ReturnsDefaultThemes() {
        Map<String, Map<String, String>> themes = resource.getThemes();

        assertNotNull(themes);
        assertTrue(themes.containsKey("default"));
        assertTrue(themes.containsKey("forest - standard"));
    }

    @Test
    void testSaveCustomTheme_Success() throws IOException {
        Map<String, Object> data = Map.of(
                "name", "testtheme",
                "variables", Map.of("--bg", "#ffffff", "--text", "#000000")
        );

        Response response = resource.saveCustomTheme(data);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertTrue(json.contains("testtheme"));
    }

    @Test
    void testSaveCustomTheme_InvalidFormat() {
        Map<String, Object> data = Map.of("name", "invalid"); // missing variables

        Response response = resource.saveCustomTheme(data);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateTheme_Success() throws IOException {
        // 1️⃣ First save an initial theme
        resource.saveCustomTheme(Map.of(
                "name", "oldtheme",
                "variables", Map.of("--bg", "#fff", "--text", "#000")
        ));

        // 2️⃣ Then update it with PUT
        Map<String, Object> updateData = Map.of(
                "newName", "updatedtheme",
                "variables", Map.of("--bg", "#000", "--text", "#fff")
        );

        Response response = resource.updateTheme("oldtheme", updateData);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertTrue(json.contains("updatedtheme"));
        assertFalse(json.contains("oldtheme"));
    }

    @Test
    void testUpdateTheme_NotFound() {
        Map<String, Object> updateData = Map.of(
                "newName", "nonexistent",
                "variables", Map.of("--bg", "#000", "--text", "#fff")
        );

        Response response = resource.updateTheme("doesnotexist", updateData);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteTheme_Success() throws IOException {
        // Save first
        resource.saveCustomTheme(Map.of(
                "name", "delete-me",
                "variables", Map.of("--bg", "#fff", "--text", "#000")
        ));

        Response response = resource.deleteTheme("delete-me");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        String json = Files.readString(tempFile.toPath());
        assertFalse(json.contains("delete-me"));
    }

    @Test
    void testDeleteTheme_NotFound() {
        Response response = resource.deleteTheme("nonexistent");
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
