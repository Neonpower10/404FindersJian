// src/main/java/nl/hu/sd/s2/sds2project2025404finders/domain/Bread.java
package nl.hu.sd.s2.sds2project2025404finders.domain;

public class Bread {
    // Note: Code uses English field names, but UI displays in Dutch
    private int id;
    private String name;
    private double price;
    private String description;
    private String photo;
    // Stock/inventory field: stores how many bread items are available
    // This will be stored in the database later when we add database support
    private int stock;
    // Published status: true if bread is visible to customers, false if it's still a concept
    // New breads start as unpublished (false) so admin can review before publishing
    private boolean published;

    /** Default constructor required. */
    public Bread() {
    }

    public Bread(int id, String name, double price, String description, String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photo = photo;
        this.stock = 0; // Default stock is 0 for backward compatibility
        this.published = false; // Default to unpublished (concept) so admin can review before publishing
    }

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    // Getter for stock/inventory
    public int getStock() { return stock; }
    // Setter for stock/inventory
    public void setStock(int stock) { this.stock = stock; }
    // Getter for published status
    public boolean isPublished() { return published; }
    // Setter for published status
    public void setPublished(boolean published) { this.published = published; }
}