package nl.hu.sd.s2.sds2project2025404finders.dashboard;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.List;

import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationData;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationService;

/**
 * Service layer that retrieves dashboard data, combining bookings API and notifications.
 */
public class DashboardService {

    private final DashboardRepository repository = new DashboardRepository();
    private final NotificationService notificationService = new NotificationService();

    public DashboardData getDashboardData() {
        DashboardData data = repository.fetchDashboardData();

        try {
            int arrivals = fetchIntFromBookingsApi("/api/bookings/today/arrivals");
            int departures = fetchIntFromBookingsApi("/api/bookings/today/departures");
            int newBookings = fetchIntFromBookingsApi("/api/bookings/new/count");

            data.setArrivals(arrivals);
            data.setDepartures(departures);
            data.setNewBookings(newBookings);
        } catch (Exception ignored) {
            // fallback: keep default repo values
        }

        // fetch notifications
        List<NotificationData> notifications = notificationService.getNewNotifications();
        data.setRecentNotifications(notifications);

        return data;
    }

    private int fetchIntFromBookingsApi(String path) throws IOException, InterruptedException {
        String base = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base + path))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
            String body = resp.body().trim();

            // Case A: plain text "5"
            try {
                return Integer.parseInt(body);
            } catch (NumberFormatException e) {
                // Case B: JSON {"count":5} or {"arrivals":3}
                if (body.contains(":")) {
                    String digits = body.replaceAll("\\D+",""); // keep only digits
                    if (!digits.isEmpty()) return Integer.parseInt(digits);
                }
                return 0;
            }
        }

        return 0;
    }
}
