package nl.hu.sd.s2.sds2project2025404finders.theme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/themes")
public class ThemeResource {
    static File CUSTOM_THEMES_FILE = new File("customThemes.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(ThemeResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<String, String>> getThemes() {
        Map<String, Map<String, String>> themes = new HashMap<>(getDefaultThemes());
        themes.putAll(loadCustomThemes());
        return themes;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCustomTheme(Map<String, Object> themeData) {
        try {
            Object rawName = themeData.get("name");
            String name = rawName != null ? rawName.toString().toLowerCase() : null;

            @SuppressWarnings("unchecked")
            Map<String, String> variables = (Map<String, String>) themeData.get("variables");

            if (name == null || name.isBlank() || variables == null || variables.isEmpty()) {
                return error("Invalid theme format", Response.Status.BAD_REQUEST);
            }


            Map<String, Map<String, String>> customThemes = loadCustomThemes();
            customThemes.put(name, variables);
            saveCustomThemes(customThemes);

            return Response.ok(Map.of("message", "Theme saved successfully")).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save theme", e);
            return error(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{oldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTheme(
            @PathParam("oldName") String oldName,
            Map<String, Object> updateData) {

        try {
            String newName = ((String) updateData.get("newName")).toLowerCase();
            @SuppressWarnings("unchecked")
            Map<String, String> variables = (Map<String, String>) updateData.get("variables");

            if (newName == null || newName.isBlank() || variables == null || variables.isEmpty()) {
                return error("Invalid update data", Response.Status.BAD_REQUEST);
            }

            Map<String, Map<String, String>> customThemes = loadCustomThemes();
            oldName = oldName.toLowerCase();

            if (!customThemes.containsKey(oldName)) {
                return error("Theme not found or not user-created", Response.Status.NOT_FOUND);
            }

            // Handle rename
            customThemes.remove(oldName);
            customThemes.put(newName, variables);
            saveCustomThemes(customThemes);

            return Response.ok(Map.of("message", "Theme updated successfully")).build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to update theme", e);
            return error("Failed to update theme: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTheme(@PathParam("name") String name) {
        Map<String, Map<String, String>> customThemes = loadCustomThemes();
        name = name.toLowerCase();

        if (!customThemes.containsKey(name)) {
            return error("Theme not found or not user-created", Response.Status.NOT_FOUND);
        }

        customThemes.remove(name);
        try {
            saveCustomThemes(customThemes);
            return Response.ok(Map.of("message", "Theme deleted successfully")).build();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete theme", e);
            return error(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Map<String, String>> getDefaultThemes() {
        Map<String, Map<String, String>> themes = new HashMap<>();
        themes.put("default", Map.of(
                "--bg", "#f8fafc",
                "--text", "#0f172a",
                "--card-bg", "#ffffff",
                "--navbar-bg", "#2563eb",
                "--accent", "#2563eb",
                "--accent-text", "#ffffff",
                "--card-text", "#6b7280"
        ));

        themes.put("forest - standard", Map.of(
                "--bg", "#ecfdf5",
                "--text", "#064e3b",
                "--card-bg", "#ffffff",
                "--navbar-bg", "#059669",
                "--accent", "#059669",
                "--accent-text", "#ffffff",
                "--card-text", "#6b7280"
        ));

        themes.put("sunset - standard", Map.of(
                "--bg", "#fff7ed",
                "--text", "#78350f",
                "--card-bg", "#ffffff",
                "--navbar-bg", "#ea580c",
                "--accent", "#ea580c",
                "--accent-text", "#ffffff",
                "--card-text", "#6b7280"
        ));

        themes.put("midnight - standard", Map.of(
                "--bg", "#0f172a",
                "--text", "#f8fafc",
                "--card-bg", "#1e293b",
                "--navbar-bg", "#6366f1",
                "--accent", "#6366f1",
                "--accent-text", "#ffffff",
                "--card-text", "#94a3b8"
        ));

        themes.put("ocean - standard", Map.of(
                "--bg", "#e0f2fe",
                "--text", "#082f49",
                "--card-bg", "#ffffff",
                "--navbar-bg", "#0284c7",
                "--accent", "#0284c7",
                "--accent-text", "#ffffff",
                "--card-text", "#6b7280"
        ));

        return themes;
    }

    private Map<String, Map<String, String>> loadCustomThemes() {
        if (!CUSTOM_THEMES_FILE.exists()) return new HashMap<>();
        try {
            return MAPPER.readValue(CUSTOM_THEMES_FILE, new TypeReference<>() {});
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load custom themes", e);
            return new HashMap<>();
        }
    }

    private void saveCustomThemes(Map<String, Map<String, String>> themes) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(CUSTOM_THEMES_FILE, themes);
    }

    private Response error(String message, Response.Status status) {
        return Response.status(status).entity(Map.of("error", message)).build();
    }
}
