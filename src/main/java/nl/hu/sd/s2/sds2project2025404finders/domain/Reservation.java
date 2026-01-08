package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int reservationId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private int productAmount;
    private Product product;
    private BigDecimal totalPrice;

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public LocalDate getReservationStartDate() { return reservationStartDate; }
    public void setReservationStartDate(LocalDate reservationStartDate) { this.reservationStartDate = reservationStartDate; }

    public LocalDate getReservationEndDate() { return reservationEndDate; }
    public void setReservationEndDate(LocalDate reservationEndDate) { this.reservationEndDate = reservationEndDate; }

    public int getProductAmount() { return productAmount; }
    public void setProductAmount(int productAmount) { this.productAmount = productAmount; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public BigDecimal calculatePrice() {
        long days = ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate);
        if (days <= 0) days = 1;

        return product.getPricePerDay()
                .multiply(BigDecimal.valueOf(days))
                .multiply(BigDecimal.valueOf(productAmount));
    }
}
