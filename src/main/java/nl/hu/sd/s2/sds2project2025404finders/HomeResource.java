package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.HomeData;

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HomeResource {

    private final HomeService homeService = new HomeService();

    /**
     * GET the home page data as JSON.
     */
    @GET
    public Response getHomeData() {
        HomeData data = homeService.getHomeData();
        // Return 200 with the HomeData object (GSON/Jackson will convert it)
        return Response.ok(data).build();
    }

    /**
     * Update the home page data.
     *
     * Note: this method expects Authorization: Bearer <token> in production.
     * For local development, if no token is present it will still accept the update.
     * Replace the simple logic below with a proper check when you enable real auth.
     */
    @POST
    public Response updateHomeData(HomeData data, @HeaderParam("Authorization") String authHeader) {
        // Development-friendly: allow missing token but log it.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // You can change this to strict rejection in production:
            // return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();

            System.out.println("updateHomeData called without Bearer token (development mode).");
        }

        homeService.updateHomeData(data);
        return Response.ok("{\"message\": \"Home page updated successfully\"}").build();
    }
}
