package nl.hu.sd.s2.sds2project2025404finders.facilities;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationData;

import java.util.List;

@Path("/facilities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FacilityResource {
    private final NotificationService notificationService = new NotificationService();

    @GET
    public List<Facility> getAllFacilities() {
        return FacilityRepository.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getFacility(@PathParam("id") int id) {
        Facility f = FacilityRepository.findById(id);
        if (f == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(f).build();
    }

    @POST
    public Response addFacility(Facility facility) {
        Facility created = FacilityRepository.add(facility);
        // Create a notification
        notificationService.createNotification(
                NotificationData.Type.BREAD_ORDER,
                String.valueOf(created.getId()),
                "New facility added: " + created.getName()
        );
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateFacility(@PathParam("id") int id, Facility facility) {
        Facility updated = FacilityRepository.update(id, facility);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFacility(@PathParam("id") int id) {
        boolean removed = FacilityRepository.delete(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
