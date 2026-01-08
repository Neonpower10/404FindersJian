package nl.hu.sd.s2.sds2project2025404finders.theme;

import nl.hu.sd.s2.sds2project2025404finders.database.ThemeDao;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/themes")
public class ThemeResource {

    private final ThemeDao themeDao = new ThemeDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<String, String>> getThemes() {
        return themeDao.getAllThemes();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTheme(Map<String, Object> themeData) {
        try {
            Object rawName = themeData.get("name");
            String name = rawName != null ? rawName.toString().toLowerCase() : null;

            @SuppressWarnings("unchecked")
            Map<String, String> variables =
                    (Map<String, String>) themeData.get("variables");

            if (name == null || name.isBlank() || variables == null || variables.isEmpty()) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Invalid theme format"))
                        .build();
            }

            themeDao.saveTheme(name, variables);
            return Response.ok(Map.of("message", "Theme saved successfully")).build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }


    @PUT
    @Path("/{oldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTheme(
            @PathParam("oldName") String oldName,
            Map<String, Object> updateData) {

        String newName = ((String) updateData.get("newName")).toLowerCase();

        @SuppressWarnings("unchecked")
        Map<String, String> variables =
                (Map<String, String>) updateData.get("variables");

        if (newName == null || newName.isBlank() || variables == null || variables.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid update data"))
                    .build();
        }

        themeDao.updateTheme(oldName.toLowerCase(), newName, variables);

        return Response.ok(Map.of("message", "Theme updated successfully")).build();
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTheme(@PathParam("name") String name) {
        themeDao.deleteTheme(name.toLowerCase());

        return Response.ok(Map.of("message", "Theme deleted successfully")).build();
    }
}
