package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Path("/rental/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    public static class CreateReservationRequest {
        public int productId;
        public String startDate;
        public String endDate;
        public int productAmount;
    }

    @GET
    public List<Reservation> getAllReservations() {
        return ReservationRepository.getAll();
    }

    @POST
    public Response createReservation(CreateReservationRequest req) {
        if (req == null || req.productAmount <= 0 || req.startDate == null || req.endDate == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Ongeldige reservering."))
                    .build();
        }

        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(req.startDate);
            end = LocalDate.parse(req.endDate);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Datumformaat moet yyyy-MM-dd zijn."))
                    .build();
        }

        if (end.isBefore(start)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Einddatum ligt vóór startdatum."))
                    .build();
        }

        Product product = ProductRepository.findById(req.productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("ok", false, "message", "Product niet gevonden."))
                    .build();
        }

        if (req.productAmount > product.getProductStock()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("ok", false, "message", "Niet genoeg voorraad."))
                    .build();
        }

        Reservation reservation = new Reservation();
        reservation.setProduct(product);
        reservation.setReservationStartDate(start);
        reservation.setReservationEndDate(end);
        reservation.setProductAmount(req.productAmount);

        BigDecimal totalPrice = reservation.calculatePrice();
        reservation.setTotalPrice(totalPrice);

        // change stock
        product.setProductStock(product.getProductStock() - req.productAmount);

        ReservationRepository.add(reservation);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of(
                        "ok", true,
                        "reservationId", reservation.getReservationId(),
                        "productId", product.getProductId(),
                        "productAmount", reservation.getProductAmount(),
                        "totalPrice", totalPrice
                ))
                .build();
    }
}
