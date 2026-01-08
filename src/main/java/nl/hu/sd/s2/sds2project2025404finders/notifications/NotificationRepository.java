package nl.hu.sd.s2.sds2project2025404finders.notifications;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory repository for notifications.
 * Replace with persistent storage in production.
 */
public class NotificationRepository {
    private final Map<String, NotificationData> store = new ConcurrentHashMap<>();

    public NotificationData save(NotificationData n) {
        store.put(n.getId(), n);
        return n;
    }

    public Optional<NotificationData> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<NotificationData> findAll() {
        ArrayList<NotificationData> list = new ArrayList<>(store.values());
        list.sort((a,b) -> b.getDate().compareTo(a.getDate())); // newest first
        return list;
    }

    public List<NotificationData> findNew() {
        List<NotificationData> res = new ArrayList<>();
        for (NotificationData n : store.values()) {
            if (n.getStatus() == NotificationData.Status.NEW) res.add(n);
        }
        res.sort((a,b) -> b.getDate().compareTo(a.getDate()));
        return res;
    }

    public void delete(String id) {
        store.remove(id);
    }
}
