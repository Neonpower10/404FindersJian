package nl.hu.sd.s2.sds2project2025404finders.notifications;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Notification;

import java.util.List;

/**
 * REST resource for notifications.
 */
@Path("/notifications")
public class NotificationResource {

    private static final NotificationService notificationService = NotificationService.getInstance();

    /**
     * Returns all notifications.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    /**
     * Returns only unread notifications.
     */
    @GET
    @Path("/unread")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    /**
     * Marks a notification as read based on booking ID.
     */
    @PATCH
    @Path("/{bookingId}/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAsRead(@PathParam("bookingId") int bookingId) {
        List<Notification> notifications = notificationService.getAllNotifications();
        for (Notification n : notifications) {
            if (n.getBookingId() == bookingId) {
                n.markAsRead();
                return Response.ok(n).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Notification not found")
                .build();
    }
}
