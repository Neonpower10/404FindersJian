// src/main/java/nl/hu/sd/s2/sds2project2025404finders/BreadDAO.java
package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Bread;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BreadDAO {
    private static final List<Bread> breads = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    // --- CRUD Methods ---
    public static List<Bread> getAll() {
        return breads;
    }

    public static Bread findById(int id) {
        return breads.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Bread add(Bread bread) {
        bread.setId(idCounter.getAndIncrement());
        breads.add(bread);
        return bread;
    }

    public static Bread update(int id, Bread updated) {
        Bread existing = findById(id);
        if (existing != null) {
            existing.setNaam(updated.getNaam());
            existing.setPrijs(updated.getPrijs());
            existing.setBeschrijving(updated.getBeschrijving());
            existing.setFoto(updated.getFoto());
        }
        return existing;
    }

    public static boolean delete(int id) {
        return breads.removeIf(b -> b.getId() == id);
    }
}