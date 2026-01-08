package nl.hu.sd.s2.sds2project2025404finders.campingdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.CampingData;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/camping")
public class CampingDataResource {

    private static final File CAMPING_FILE = new File("campingData.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(CampingDataResource.class.getName());

    /**
     * GET — returns all camping items (default + custom)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CampingData> getCampingData() {
        List<CampingData> data = new ArrayList<>(getDefaultCampingData());
        data.addAll(loadCustomCampingData());
        return data;
    }

    /**
     * POST — add a new camping build
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCampingData(CampingData incoming) {
        try {
            if (incoming.getName() == null || incoming.getName().isBlank()) {
                return error("Camping item must have a name", Response.Status.BAD_REQUEST);
            }

            List<CampingData> customList = loadCustomCampingData();

            // simple auto-id: next highest
            int newId = customList.stream().mapToInt(CampingData::getId).max().orElse(0) + 1;
            incoming.setId(newId);

            customList.add(incoming);

            saveCustomCampingData(customList);

            return Response.ok(Map.of("message", "Camping data saved successfully")).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save camping data", e);
            return error("Save failed: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT — update existing camping item
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCampingData(
            @PathParam("id") int id,
            CampingData update
    ) {
        try {
            List<CampingData> customList = loadCustomCampingData();

            Optional<CampingData> existingOpt = customList.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst();

            if (existingOpt.isEmpty()) {
                return error("Camping item not found or not user-created", Response.Status.NOT_FOUND);
            }

            CampingData existing = existingOpt.get();

            // update fields
            if (update.getName() != null) existing.setName(update.getName());
            if (update.getDescription() != null) existing.setDescription(update.getDescription());

            saveCustomCampingData(customList);

            return Response.ok(Map.of("message", "Camping data updated successfully")).build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to update camping data", e);
            return error("Update failed: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE — remove camping item
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCampingData(@PathParam("id") int id) {
        try {
            List<CampingData> customList = loadCustomCampingData();

            boolean removed = customList.removeIf(c -> c.getId() == id);

            if (!removed) {
                return error("Camping item not found or not user-created", Response.Status.NOT_FOUND);
            }

            saveCustomCampingData(customList);
            return Response.ok(Map.of("message", "Camping data deleted successfully")).build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete camping data", e);
            return error("Delete failed: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    // ────────────────────────────────────────
    // Helper methods
    // ────────────────────────────────────────

    private List<CampingData> getDefaultCampingData() {
        return List.of(
                new CampingData(1, "Forest Starter Pack", "Basic camping gear"),
                new CampingData(2, "Mountain Explorer", "Good for rough terrain"),
                new CampingData(3, "Ultra-Light Solo Kit", "Minimalist equipment")
        );
    }

    private List<CampingData> loadCustomCampingData() {
        if (!CAMPING_FILE.exists()) return new ArrayList<>();

        try {
            return MAPPER.readValue(CAMPING_FILE, new TypeReference<List<CampingData>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load camping data", e);
            return new ArrayList<>();
        }
    }

    private void saveCustomCampingData(List<CampingData> list) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(CAMPING_FILE, list);
    }

    private Response error(String message, Response.Status status) {
        return Response.status(status).entity(Map.of("error", message)).build();
    }
}
