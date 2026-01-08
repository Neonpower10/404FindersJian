package nl.hu.sd.s2.sds2project2025404finders.dashboard;

import nl.hu.sd.s2.sds2project2025404finders.booking.BookingResource;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Repository class responsible for fetching dashboard data.
 * Fetches real counts from BookingResource and NotificationService.
 */
public class DashboardRepository {

    private final BookingResource bookingResource = new BookingResource();
    private final NotificationService notificationService = new NotificationService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public DashboardData fetchDashboardData() {
        DashboardData data = new DashboardData();

        List<Booking> bookings = bookingResource.bookings();
        LocalDate today = LocalDate.now();

        int arrivals = 0;
        int departures = 0;
        int newBookings = 0;

        for (Booking b : bookings) {
            if (b.getStartDate() != null && !b.getStartDate().isEmpty()) {
                LocalDate start = LocalDate.parse(b.getStartDate(), formatter);
                if (start.equals(today)) arrivals++;
            }
            if (b.getEndDate() != null && !b.getEndDate().isEmpty()) {
                LocalDate end = LocalDate.parse(b.getEndDate(), formatter);
                if (end.equals(today)) departures++;
            }
//            if (b.getCreatedAt() != null && !b.getCreatedAt().isEmpty()) {
//                LocalDate created = LocalDate.parse(b.getCreatedAt(), formatter);
//                if (created.equals(today)) newBookings++;
//            }
        }

        data.setArrivals(arrivals);
        data.setDepartures(departures);
        data.setNewBookings(newBookings);

        // Fetch new notifications
        List<NotificationData> notifications = notificationService.getNewNotifications();
        data.setRecentNotifications(notifications);

        // You could also set newMessages from a contact API here if available
        // data.setNewMessages(...);

        return data;
    }
}
