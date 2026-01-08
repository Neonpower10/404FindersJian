package nl.hu.sd.s2.sds2project2025404finders.renting;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.util.List;

@Path("/reservations")
public class ReservationResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> getAllReservations() {
        return Reservation.getReservations();
    }

    @GET
    @Path("/{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("reservationId") int reservationId) {
        for (Reservation r : Reservation.getReservations()) {
            if (r.getReservationId() == reservationId) {
                return Response.ok(r).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reservatie niet gevonden").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReservation(Reservation reservation) {
        Reservation.getReservations().add(reservation);
        return Response.status(Response.Status.CREATED).entity(reservation).build();
    }

    @DELETE
    @Path("/{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@PathParam ("reservationId") int reservationId) {
        List<Reservation> reservations = Reservation.getReservations();

        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                reservations.remove(reservation);
                return Response.noContent().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Reservatie niet gevonden").build();
    }

    @PATCH
    @Path("/{reservationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReservation(@PathParam ("reservationId") int reservationId, Reservation updatedReservation) {
        List<Reservation> reservations = Reservation.getReservations();

        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                if (updatedReservation.getReservationStartDate() != null) {
                    r.setReservationStartDate(updatedReservation.getReservationStartDate());
                }
                if (updatedReservation.getReservationEndDate() != null) {
                    r.setReservationEndDate(updatedReservation.getReservationEndDate());
                }

                if (updatedReservation.getProductAmount() != 0) {
                    r.setProductAmount(updatedReservation.getProductAmount());
                }

                return Response.ok(r).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reservatie niet gevonden").build();
    }
}
