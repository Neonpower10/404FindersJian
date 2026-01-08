package nl.hu.sd.s2.sds2project2025404finders.dashboard;

import java.util.List;
import nl.hu.sd.s2.sds2project2025404finders.notifications.NotificationData;

/**
 * DTO: dashboard aggregate that is returned to the frontend.
 */
public class DashboardData {
    private int newMessages;
    private int newBookings;
    private int arrivals;
    private int departures;
    private List<NotificationData> recentNotifications;

    // existing getters/setters...
    public int getNewMessages() { return newMessages; }
    public void setNewMessages(int newMessages) { this.newMessages = newMessages; }

    public int getNewBookings() { return newBookings; }
    public void setNewBookings(int newBookings) { this.newBookings = newBookings; }

    public int getArrivals() { return arrivals; }
    public void setArrivals(int arrivals) { this.arrivals = arrivals; }

    public int getDepartures() { return departures; }
    public void setDepartures(int departures) { this.departures = departures; }

    public List<NotificationData> getRecentNotifications() { return recentNotifications; }
    public void setRecentNotifications(List<NotificationData> recentNotifications) { this.recentNotifications = recentNotifications; }
}
