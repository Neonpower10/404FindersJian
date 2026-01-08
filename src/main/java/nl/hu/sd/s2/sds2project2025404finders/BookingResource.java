package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import nl.hu.sd.s2.sds2project2025404finders.domain.Camping;

import java.util.List;

/**
 * REST resource class that handles HTTP requests related to bookings.
 * <p>
 * This class exposes an endpoint that returns all bookings associated
 * with a camping. The data is currently mocked using the {@link Camping#getCamping()} method.
 * </p>
 */
@Path("/bookings")
public class BookingResource {
    private static final Camping camping = Camping.getCamping(); // only for now, when we switch to actual database remove the private static final part

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> bookings() {
        return camping.getBookings();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(Booking newBooking) {
        newBooking.setBookingId(camping.getBookings().size() + 1);
        camping.getBookings().add(newBooking);
        return Response.status(Response.Status.CREATED).entity(newBooking).build();
    }
}

