package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.util.List;
import java.util.Map;

@Path("/admin/rental/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class RentalAdminReservationResource {

    @GET
    public List<Reservation> getAllReservations() {
        return ReservationRepository.getAll();
    }

    @DELETE
    @Path("/{id}")
    public Response cancelReservation(@PathParam("id") int id) {
        Reservation r = ReservationRepository.findById(id);
        if (r == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("ok", false, "message", "Reservering niet gevonden."))
                    .build();
        }

        if (r.getProduct() != null) {
            r.getProduct().setProductStock(
                    r.getProduct().getProductStock() + r.getProductAmount()
            );
        }

        ReservationRepository.delete(id);

        return Response.ok(Map.of("ok", true, "message", "Reservering geannuleerd")).build();
    }
}
