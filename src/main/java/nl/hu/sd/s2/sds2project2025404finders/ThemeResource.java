package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * REST resource that provides available UI themes for the frontend.
 * <p>
 * Each theme defines a set of CSS variables mapped to their respective color values.
 * The frontend fetches these values dynamically and applies them to style the UI.
 * </p>
 */
@Path("/themes")
public class ThemeResource {

    /**
     * Handles GET requests for the /themes endpoint.
     * <p>
     * Returns a map of available themes. Each theme contains CSS variable names
     * and their corresponding color values.
     * </p>
     *
     * @return a {@link Map} where each key is a theme name and the value is a map of CSS variables
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<String, String>> getThemes() {
        Map<String, Map<String, String>> themes = new HashMap<>();

        themes.put("default", Map.of(
                "--bg", "#f8fafc",
                "--text", "#0f172a",
                "--card-bg", "#ffffff",
                "--accent", "#2563eb",
                "--muted", "#6b7280"
        ));

        themes.put("forest", Map.of(
                "--bg", "#ecfdf5",
                "--text", "#064e3b",
                "--card-bg", "#ffffff",
                "--accent", "#059669",
                "--muted", "#6b7280"
        ));

        themes.put("sunset", Map.of(
                "--bg", "#fff7ed",
                "--text", "#78350f",
                "--card-bg", "#ffffff",
                "--accent", "#ea580c",
                "--muted", "#6b7280"
        ));

        themes.put("midnight", Map.of(
                "--bg", "#0f172a",
                "--text", "#f8fafc",
                "--card-bg", "#1e293b",
                "--accent", "#6366f1",
                "--muted", "#94a3b8",
                "--btn-ghost-text", "#ffffff"
        ));

        themes.put("ocean", Map.of(
                "--bg", "#e0f2fe",
                "--text", "#082f49",
                "--card-bg", "#ffffff",
                "--accent", "#0284c7",
                "--muted", "#6b7280"
        ));

        return themes;
    }
}
