package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

    /**
     * Handles GET requests for the /bookings endpoint.
     * <p>
     * Returns a list of all bookings as JSON. The data is retrieved
     * from a static Camping instance that contains mock booking data.
     * </p>
     *
     * @return a {@link List} of {@link Booking} objects in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> bookings() {
        Camping camping = Camping.getCamping();
        return camping.getBookings();
    }
}
