package nl.hu.sd.s2.sds2project2025404finders.booking;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import nl.hu.sd.s2.sds2project2025404finders.domain.Camping;
import nl.hu.sd.s2.sds2project2025404finders.domain.Notification;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;

import java.util.List;

/**
 * REST resource class that handles HTTP requests
 * related to bookings.
 * <p>
 * Exposes endpoints to get, add, update, and delete bookings.
 * Automatically creates notifications for new bookings, arrivals, and departures.
 * </p>
 */
@Path("/bookings")
public class BookingResource {

    private static final Camping camping = Camping.getCamping();
    private static final NotificationService notificationService = NotificationService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> bookings() {
        return camping.getBookings();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") int id) {
        for (Booking b : camping.getBookings()) {
            if (b.getBookingId() == id) {
                return Response.ok(b).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Booking not found")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(Booking newBooking) {
        int maxId = camping.getBookings().stream()
                .mapToInt(Booking::getBookingId)
                .max()
                .orElse(0);

        newBooking.setBookingId(maxId + 1);
        camping.getBookings().add(newBooking);

        // Create notification for new booking
        notificationService.addNotification(new Notification(
                newBooking.getBookingId(),
                newBooking.getPersonFirstName(),
                newBooking.getPersonLastName(),
                "Nieuwe boeking aangemaakt"
        ));

        return Response.status(Response.Status.CREATED).entity(newBooking).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBooking(@PathParam("id") int id) {
        Booking toDelete = null;
        for (Booking b : camping.getBookings()) {
            if (b.getBookingId() == id) {
                toDelete = b;
                break;
            }
        }

        if (toDelete == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Booking not found")
                    .build();
        }

        camping.getBookings().remove(toDelete);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") int id, Booking updatedBooking) {
        for (Booking b : camping.getBookings()) {
            if (b.getBookingId() == id) {

                if (updatedBooking.getPersonFirstName() != null)
                    b.setPersonFirstName(updatedBooking.getPersonFirstName());
                if (updatedBooking.getPersonLastName() != null)
                    b.setPersonLastName(updatedBooking.getPersonLastName());
                if (updatedBooking.getEmail() != null)
                    b.setEmail(updatedBooking.getEmail());
                if (updatedBooking.getPhoneNumber() != null)
                    b.setPhoneNumber(updatedBooking.getPhoneNumber());
                if (updatedBooking.getStreetName() != null)
                    b.setStreetName(updatedBooking.getStreetName());
                if (updatedBooking.getHouseNumber() != null)
                    b.setHouseNumber(updatedBooking.getHouseNumber());
                if (updatedBooking.getPostcode() != null)
                    b.setPostcode(updatedBooking.getPostcode());
                if (updatedBooking.getPlace() != null)
                    b.setPlace(updatedBooking.getPlace());
                if (updatedBooking.getCountry() != null)
                    b.setCountry(updatedBooking.getCountry());
                if (updatedBooking.getCampPlace() != null)
                    b.setCampPlace(updatedBooking.getCampPlace());
                if (updatedBooking.getStartDate() != null)
                    b.setStartDate(updatedBooking.getStartDate());
                if (updatedBooking.getEndDate() != null)
                    b.setEndDate(updatedBooking.getEndDate());
                if (updatedBooking.getAmountPersons() != 0)
                    b.setAmountPersons(updatedBooking.getAmountPersons());

                return Response.ok(b).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Booking not found")
                .build();
    }
}
