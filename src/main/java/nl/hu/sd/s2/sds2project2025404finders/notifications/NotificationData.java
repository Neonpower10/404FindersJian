package nl.hu.sd.s2.sds2project2025404finders.notifications;

import java.time.Instant;

/**
 * Notification DTO used by the API.
 */
public class NotificationData {
    public enum Type { BOOKING, BREAD_ORDER, FACILITY, CONTACT_MESSAGE }
    public enum Status { NEW, HANDLED }

    private String id;
    private Type type;
    private Instant date;
    private String description;
    private String objectId; // id of the related booking/order/message
    private Status status;

    public NotificationData() {}

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
