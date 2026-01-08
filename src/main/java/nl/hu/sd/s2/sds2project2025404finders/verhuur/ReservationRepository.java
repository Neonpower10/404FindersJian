package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import java.util.*;

public class ReservationRepository {

    private Map<Integer, Reservation> reservations = new HashMap<>();
    private int idCounter = 1;

    public Reservation save(Reservation r) {
        if (r.getId() == 0) r.setId(idCounter++);
        reservations.put(r.getId(), r);
        return r;
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public boolean delete(int id) {
        return reservations.remove(id) != null;
    }
}