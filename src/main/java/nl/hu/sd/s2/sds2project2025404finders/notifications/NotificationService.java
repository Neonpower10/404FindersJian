package nl.hu.sd.s2.sds2project2025404finders.notifications;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Business logic for notifications.
 */
public class NotificationService {
    private final NotificationRepository repository = new NotificationRepository();

    public NotificationData createNotification(NotificationData.Type type, String objectId, String description) {
        NotificationData n = new NotificationData();
        n.setId(UUID.randomUUID().toString());
        n.setType(type);
        n.setObjectId(objectId);
        n.setDescription(description);
        n.setDate(Instant.now());
        n.setStatus(NotificationData.Status.NEW);
        return repository.save(n);
    }

    public List<NotificationData> getAllNotifications() { return repository.findAll(); }

    public List<NotificationData> getNewNotifications() { return repository.findNew(); }

    public boolean markHandled(String id) {
        var opt = repository.findById(id);
        if (opt.isPresent()) {
            var n = opt.get();
            n.setStatus(NotificationData.Status.HANDLED);
            repository.save(n);
            return true;
        }
        return false;
    }

    public void deleteNotification(String id) { repository.delete(id); }
}
