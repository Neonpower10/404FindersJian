package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private int productId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private int productAmount;

    public Reservation() {}

    public Reservation(int id, int productId, LocalDate reservationStartDate,
                       LocalDate reservationEndDate, int productAmount) {
        this.id = id;
        this.productId = productId;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.productAmount = productAmount;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public LocalDate getReservationStartDate() { return reservationStartDate; }
    public LocalDate getReservationEndDate() { return reservationEndDate; }
    public int getProductAmount() { return productAmount; }

    public void setId(int id) { this.id = id; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setReservationStartDate(LocalDate reservationStartDate) { this.reservationStartDate = reservationStartDate; }
    public void setReservationEndDate(LocalDate reservationEndDate) { this.reservationEndDate = reservationEndDate; }
    public void setProductAmount(int productAmount) { this.productAmount = productAmount; }

    public BigDecimal calculatePrice(BigDecimal pricePerDay) {
        long days = reservationStartDate.until(reservationEndDate).getDays();
        return pricePerDay.multiply(BigDecimal.valueOf(days * productAmount));
    }
}