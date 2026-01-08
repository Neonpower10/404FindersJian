package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationRepository {
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static List<Reservation> getAll() {
        return reservations;
    }

    public static Reservation findById(int id) {
        return reservations.stream()
                .filter(r -> r.getReservationId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Reservation add(Reservation reservation) {
        if (reservation.getReservationId() == 0) {
            reservation.setReservationId(idCounter.getAndIncrement());
        }
        reservations.add(reservation);
        return reservation;
    }

    public static boolean delete(int id) {
        return reservations.removeIf(r -> r.getReservationId() == id);
    }

    public static void clearAll() {
        reservations.clear();
        idCounter.set(1);
    }
}
