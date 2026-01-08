package nl.hu.sd.s2.sds2project2025404finders.domain;

/**
 * Represents a bread order made by a customer.
 * This class stores information about who ordered bread and what was ordered.
 * Each BreadOrder represents one complete order with customer information
 * and the bread items that were ordered.
 */
public class BreadOrder {
    // --- General ---
    private int orderId;
    
    // --- Customer info ---
    private String customerFirstName;
    private String customerLastName;
    private String phoneNumber;
    private String email; // Optional, for future use
    
    // --- Order info ---
    private String orderDate; // Date when the order was placed (DD-MM-YYYY format)
    private double totalPrice; // Total price of the order in euros
    
    // --- Order items ---
    // This stores the order items as a JSON-like string
    // Format: "[{\"breadId\":1,\"breadName\":\"Wit brood\",\"quantity\":2,\"price\":3.50},...]"
    private String orderItems; // JSON string of items in the order
    
    /**
     * Default constructor for creating an empty BreadOrder object.
     * Required for JSON serialization.
     */
    public BreadOrder() {
    }
    
    /**
     * Constructor to create a BreadOrder with all details.
     * 
     * @param orderId Unique order identifier
     * @param customerFirstName First name of the customer
     * @param customerLastName Last name of the customer
     * @param phoneNumber Phone number of the customer
     * @param email Email address (can be null)
     * @param orderDate Date when order was placed (DD-MM-YYYY)
     * @param totalPrice Total price of the order
     * @param orderItems JSON string containing the ordered items
     */
    public BreadOrder(int orderId, String customerFirstName, String customerLastName,
                     String phoneNumber, String email, String orderDate, 
                     double totalPrice, String orderItems) {
        this.orderId = orderId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }
    
    // --- Getters & Setters ---
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public String getCustomerFirstName() {
        return customerFirstName;
    }
    
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
    
    public String getCustomerLastName() {
        return customerLastName;
    }
    
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }
    
    /**
     * Returns a string representation of the bread order. Useful for debugging.
     */
    @Override
    public String toString() {
        return "BreadOrder{" +
                "orderId=" + orderId +
                ", customer='" + customerFirstName + " " + customerLastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
