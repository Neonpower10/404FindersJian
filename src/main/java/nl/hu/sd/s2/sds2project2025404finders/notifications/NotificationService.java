package nl.hu.sd.s2.sds2project2025404finders.notifications;

import nl.hu.sd.s2.sds2project2025404finders.domain.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton service that manages notifications in-memory.
 * Notifications are linked to bookings.
 */
public class NotificationService {

    private static NotificationService instance;

    // Stores all notifications
    private final List<Notification> notifications;

    // private constructor for singleton
    private NotificationService() {
        this.notifications = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of NotificationService.
     */
    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    /**
     * Adds a new notification.
     *
     * @param notification The notification to add
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    /**
     * Returns all notifications.
     */
    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notifications); // defensive copy
    }

    /**
     * Clears all notifications (useful for tests).
     */
    public void clearAllNotifications() {
        notifications.clear();
    }

    /**
     * Returns all notifications that are unread.
     */
    public List<Notification> getUnreadNotifications() {
        List<Notification> unread = new ArrayList<>();
        for (Notification n : notifications) {
            if (!n.isRead()) unread.add(n);
        }
        return unread;
    }
    public void clearNotifications() {
        notifications.clear(); // assuming `notifications` is your internal List<Notification>
    }

}
