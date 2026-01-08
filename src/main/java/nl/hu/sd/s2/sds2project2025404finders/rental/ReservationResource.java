package nl.hu.sd.s2.sds2project2025404finders.rental;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @GET
    public List<Reservation> getAllReservations() {
        return ReservationRepository.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") int id) {
        Reservation r = ReservationRepository.findById(id);
        if (r == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(r).build();
    }

    @POST
    public Response addReservation(Reservation reservation) {
        Reservation created = ReservationRepository.add(reservation);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateReservation(@PathParam("id") int id, Reservation reservation) {
        Reservation updated = ReservationRepository.update(id, reservation);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") int id) {
        boolean removed = ReservationRepository.delete(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
