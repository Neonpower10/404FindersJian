package nl.hu.sd.s2.sds2project2025404finders.rental;

import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationRepository {
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static List<Reservation> getAll() {
        return reservations;
    }

    public static Reservation findById(int id) {
        return reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Reservation add(Reservation reservation) {
        reservation.setId(idCounter.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    public static Reservation update(int id, Reservation updated) {
        Reservation existing = findById(id);
        if (existing != null) {
            existing.setReservationStartDate(updated.getReservationStartDate());
            existing.setReservationEndDate(updated.getReservationEndDate());
            existing.setProductAmount(updated.getProductAmount());
            existing.setProduct(updated.getProduct());
        }
        return existing;
    }

    public static boolean delete(int id) {
        return reservations.removeIf(r -> r.getId() == id);
    }
}
