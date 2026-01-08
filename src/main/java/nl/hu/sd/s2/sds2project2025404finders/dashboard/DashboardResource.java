package nl.hu.sd.s2.sds2project2025404finders.dashboard;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * REST resource that provides dashboard data to the frontend
 */
@Path("/dashboard")
public class DashboardResource {

    private final DashboardService service = new DashboardService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboardData() {
        DashboardData data = service.getDashboardData();
        return Response.ok(data).build();
    }
}