package nl.hu.sd.s2.sds2project2025404finders.contact;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationData;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Path("/contactPage")
public class ContactController {
    private final NotificationService notificationService = new NotificationService();
    private static final List<ContactForm> messages = new ArrayList<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleContact(ContactForm form) {
        messages.add(form);

        // Create a notification
        notificationService.createNotification(
                NotificationData.Type.CONTACT_MESSAGE,
                form.getEmail(),
                "New contact message from " + form.getName()
        );

        System.out.println("Messages list: " + messages);

        return Response.ok(Map.of(
                "message", "Received message from " + form.getName() +
                        " (" + form.getEmail() + "): " + form.getMessage()
        )).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactPage() {
        return Response.ok(messages).build();
    }
}
