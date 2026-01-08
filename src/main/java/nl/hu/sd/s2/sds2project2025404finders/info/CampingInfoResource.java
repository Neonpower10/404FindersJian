package nl.hu.sd.s2.sds2project2025404finders.info;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/camping-info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CampingInfoResource {

    /**
     * Returns the current camping information.
     * GET /api/camping-info
     */
    @GET
    public Response getCampingInfo() {
        CampingInfo info = CampingInfoRepository.getCampingInfo();
        return Response.ok(info).build();
    }

    /**
     * Replaces the current camping information with the data sent by the client.
     * This can be called from an admin page in the future.
     *
     * PUT /api/camping-info
     */
    @PUT
    public Response updateCampingInfo(CampingInfo updatedInfo) {
        if (updatedInfo == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        CampingInfo updated = CampingInfoRepository.updateCampingInfo(updatedInfo);
        return Response.ok(updated).build();
    }
}

