package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FacilityRepository {
    private static final List<Facility> facilities = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static List<Facility> getAll() {
        return facilities;
    }

    public static Facility findById(int id) {
        return facilities.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Facility add(Facility facility) {
        facility.setId(idCounter.getAndIncrement());
        facilities.add(facility);
        return facility;
    }

    public static Facility update(int id, Facility updated) {
        Facility existing = findById(id);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setImage(updated.getImage());
            existing.setStatus(updated.getStatus());
        }
        return existing;
    }

    public static boolean delete(int id) {
        return facilities.removeIf(f -> f.getId() == id);
    }
}
