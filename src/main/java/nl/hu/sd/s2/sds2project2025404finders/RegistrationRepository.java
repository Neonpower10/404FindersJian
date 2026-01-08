package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RegistrationRepository {
    private static final List<Registration> registrations = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    static public Registration getRegistration() {
        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(1, "Jian", "Grafhorst", "hello123", "kookiemonster@gmail.com"));
        return new Registration();
    }


    public static List<Registration> getAll() {
        return registrations;
    }

    public static Registration findById(int registrationId) {
        return registrations.stream()
                .filter(f -> f.getRegistrationId() == registrationId)
                .findFirst()
                .orElse(null);
    }

    public static Registration add(Registration registration) {
        registration.setRegistrationId(idCounter.getAndIncrement());
        registrations.add(registration);
        return registration;
    }

    public static Registration update(int registrationId, Registration updated) {
        Registration existing = findById(registrationId);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setSurname(updated.getSurname());
            existing.setPassword(updated.getPassword());
            existing.setEmail(updated.getEmail());

        }
        return existing;

    }
    public static boolean delete(int id) {
        return registrations.removeIf(f -> f.getRegistrationId() == id);
    }

}