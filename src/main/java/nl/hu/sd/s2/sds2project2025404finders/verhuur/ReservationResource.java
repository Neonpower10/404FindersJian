package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    private ReservationService service = new ReservationService();

    @GET
    public Response getAll() {
        return Response.ok(service.getAllReservations()).build();
    }

    @POST
    public Response create(Reservation r) {
        try {
            Reservation created = service.createReservation(r);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (service.deleteReservation(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }
}