package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int id;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private int productAmount;
    private Product product;

    public Reservation() {}

    public Reservation(int id, LocalDate reservationStartDate, LocalDate reservationEndDate,
                       int productAmount, Product product) {
        this.id = id;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.productAmount = productAmount;
        this.product = product;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getReservationStartDate() { return reservationStartDate; }
    public void setReservationStartDate(LocalDate reservationStartDate) { this.reservationStartDate = reservationStartDate; }

    public LocalDate getReservationEndDate() { return reservationEndDate; }
    public void setReservationEndDate(LocalDate reservationEndDate) { this.reservationEndDate = reservationEndDate; }

    public int getProductAmount() { return productAmount; }
    public void setProductAmount(int productAmount) { this.productAmount = productAmount; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public boolean isLoggedIn() {
        return false;
    }

    public BigDecimal calculatePrice() {
        if (product == null || product.getPricePerDay() == null) return null;
        if (reservationStartDate == null || reservationEndDate == null) return null;

        long days = ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate) + 1;
        if (days <= 0) return BigDecimal.ZERO;

        return product.getPricePerDay()
                .multiply(BigDecimal.valueOf(days))
                .multiply(BigDecimal.valueOf(productAmount));
    }
}
