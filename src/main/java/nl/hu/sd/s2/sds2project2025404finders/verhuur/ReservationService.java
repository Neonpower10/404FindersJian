package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private ReservationRepository reservationRepo = new ReservationRepository();
    private ProductRepository productRepo = new ProductRepository();

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation createReservation(Reservation r) {
        validateReservation(r);

        Product p = productRepo.findById(r.getProductId());
        if (p == null) {
            throw new IllegalArgumentException("Product does not exist");
        }

        // Check stock
        if (p.getProductStock() < r.getProductAmount()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        // Reduce stock
        p.setProductStock(p.getProductStock() - r.getProductAmount());
        productRepo.save(p);

        // Save reservation
        return reservationRepo.save(r);
    }

    public boolean deleteReservation(int id) {
        return reservationRepo.delete(id);
    }

    private void validateReservation(Reservation r) {

        LocalDate start = r.getReservationStartDate();
        LocalDate end = r.getReservationEndDate();

        if (start == null || end == null)
            throw new IllegalArgumentException("Start and end dates required");

        if (!end.isAfter(start))
            throw new IllegalArgumentException("End date must be after start date");

        if (r.getProductAmount() <= 0)
            throw new IllegalArgumentException("Reservation must be at least 1 item");
    }
}