package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a reservation made by a customer for a rental product.
 */
public class Reservation {
    private int reservationId;
    private int productId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private int productAmount;
    private String customerName;
    private BigDecimal pricePerDay;

    /**
     * Default constructor required for JSON serialization.
     */
    public Reservation() {
    }

    public Reservation(int productId, LocalDate reservationStartDate, LocalDate reservationEndDate,
                      int productAmount, String customerName, BigDecimal pricePerDay) {
        this.productId = productId;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.productAmount = productAmount;
        this.customerName = customerName;
        this.pricePerDay = pricePerDay;
    }

    /**
     * Calculates the total price for this reservation.
     * 
     * Formula: (number of days) * (price per day) * (number of items)
     * 
     * @return The total price as a BigDecimal (for precise money calculations)
     */
    public BigDecimal calculatePrice() {
        long numberOfDays = ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate) + 1;
        BigDecimal days = BigDecimal.valueOf(numberOfDays);
        BigDecimal amount = BigDecimal.valueOf(productAmount);
        return days.multiply(pricePerDay).multiply(amount);
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDate getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(LocalDate reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public LocalDate getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDate reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}

