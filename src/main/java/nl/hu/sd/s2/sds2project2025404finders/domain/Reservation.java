package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int reservationId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private Product product;
    private int productAmount;

    public Reservation() {
    }

    public Reservation(int reservationId, LocalDate reservationStartDate, LocalDate reservationEndDate, Product product, int productAmount) {
        this.reservationId = reservationId;

        if (reservationStartDate == null) {
            throw new IllegalArgumentException("Startdatum mag niet leeg zijn");
        }
        if (reservationStartDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Startdatum kan niet in het verleden zijn");
        }
        this.reservationStartDate = reservationStartDate;

        if (reservationEndDate == null) {
            throw new IllegalArgumentException("Einddatum mag niet leeg zijn");
        }
        if (reservationEndDate.isBefore(reservationStartDate)) {
            throw new IllegalArgumentException("Einddatum kan niet eerder dan de startdatum zijn");
        }
        this.reservationEndDate = reservationEndDate;

        this.product = product;

        if (productAmount <= 0) {
            throw new IllegalArgumentException("Aantal mag niet 0 of minder zijn");
        }
        this.productAmount = productAmount;
    }

    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();

        List<Product> products = Product.getProducts();
        Product fiets = products.get(0);
        Product kano = products.get(1);

        reservations.add(new Reservation(1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), fiets, 2));

        reservations.add(new Reservation(2, LocalDate.now().plusDays(5), LocalDate.now().plusDays(7), kano, 4));

        return reservations;
    }

    public int getReservationId() {
        return reservationId;
    }

    public LocalDate getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(LocalDate reservationStartDate) {
        if (reservationStartDate == null) {
            throw new IllegalArgumentException("Startdatum mag niet leeg zijn");
        }
        if (reservationStartDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Startdatum kan niet in het verleden zijn");
        }
        this.reservationStartDate = reservationStartDate;
    }

    public LocalDate getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDate reservationEndDate) {
        if (reservationEndDate == null) {
            throw new IllegalArgumentException("Einddatum mag niet leeg zijn");
        }
        if (this.reservationStartDate == null) {
            throw new IllegalArgumentException("Startdatum moet nog worden ingevuld");
        }
        if (reservationEndDate.isBefore(reservationStartDate)) {
            throw new IllegalArgumentException("Einddatum kan niet eerder dan de startdatum zijn");
        }
        this.reservationEndDate = reservationEndDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        if (productAmount <= 0) {
            throw new IllegalArgumentException("Aantal mag niet 0 of minder zijn");
        }
        this.productAmount = productAmount;
    }

    @Override
    public String toString() {
        return "ProductReservation{" +
                ", reservationId=" + reservationId +
                ", reservationStartDate=" + reservationStartDate +
                ", reservationEndDate=" + reservationEndDate +
                ", product=" + (product != null ? product.getProductName() : "null") +
                ", productAmount=" + productAmount +
                '}';
    }

}
