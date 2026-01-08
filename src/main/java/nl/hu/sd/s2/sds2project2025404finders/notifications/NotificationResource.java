package nl.hu.sd.s2.sds2project2025404finders.notifications;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for notifications.
 */
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    private final NotificationService service = new NotificationService();

    @GET
    public Response getAll() {
        List<NotificationData> list = service.getAllNotifications();
        return Response.ok(list).build();
    }

    @GET
    @Path("/new")
    public Response getNew() {
        return Response.ok(service.getNewNotifications()).build();
    }

    @POST
    public Response create(NotificationCreateRequest req) {
        NotificationData.Type t = NotificationData.Type.valueOf(req.getType());
        NotificationData created = service.createNotification(t, req.getObjectId(), req.getDescription());
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/handled")
    public Response markHandled(@PathParam("id") String id) {
        boolean ok = service.markHandled(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }

    // DTO for create
    public static class NotificationCreateRequest {
        private String type; // "BOOKING","BREAD_ORDER","CONTACT_MESSAGE"
        private String objectId;
        private String description;
        public NotificationCreateRequest() {}
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getObjectId() { return objectId; }
        public void setObjectId(String objectId) { this.objectId = objectId; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
