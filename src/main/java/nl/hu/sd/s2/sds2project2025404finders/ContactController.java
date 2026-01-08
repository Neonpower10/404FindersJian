package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

@Path("/contactPage")
public class ContactController {

    private static final List<ContactForm> messages = new ArrayList<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleContact(ContactForm form) {
        messages.add(form);
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
