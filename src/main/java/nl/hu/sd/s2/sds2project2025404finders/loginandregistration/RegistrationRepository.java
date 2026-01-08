package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;

import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.mindrot.jbcrypt.BCrypt;

public class RegistrationRepository {
    private static final List<Registration> registrations = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    static {
        Registration admin = new Registration();
        admin.setRegistrationId(idCounter.getAndIncrement());
        admin.setName("Admin");
        admin.setSurname("PO");
        admin.setEmail("admin@camping.nl");
        admin.setPassword(BCrypt.hashpw("Admin123!", BCrypt.gensalt())); // hashed admin wachtwoord
        registrations.add(admin);
    }

    public static List<Registration> getAll() { return registrations; }

    public static Registration findById(int registrationId) {
        return registrations.stream()
                .filter(r -> r.getRegistrationId() == registrationId)
                .findFirst()
                .orElse(null);
    }

    public static Registration findByEmail(String email) {
        return registrations.stream()
                .filter(r -> r.getEmail() != null && r.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public static Registration findByEmailAndPassword(String email, String password) {
        Registration found = registrations.stream()
                .filter(r -> r.getEmail() != null && r.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (found == null) return null;

        // check password via Bcrypt
        if (found.getPassword() == null || !BCrypt.checkpw(password, found.getPassword())) {
            return null;
        }

        return found;
    }

    public static Registration add(Registration registration) {
        if (registration.getRegistrationId() == 0) {
            registration.setRegistrationId(idCounter.getAndIncrement());
        }

        // hash password
        if (registration.getPassword() != null) {
            String hashed = BCrypt.hashpw(registration.getPassword(), BCrypt.gensalt());
            registration.setPassword(hashed);
        }

        registrations.add(registration);
        return registration;
    }

    public static Registration update(int registrationId, Registration updated) {
        Registration existing = findById(registrationId);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setSurname(updated.getSurname());
            existing.setEmail(updated.getEmail());

            if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
                existing.setPassword(BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt()));
            }
        }

        return existing;
    }

    public static boolean delete(int id) {
        return registrations.removeIf(r -> r.getRegistrationId() == id);
    }
}
