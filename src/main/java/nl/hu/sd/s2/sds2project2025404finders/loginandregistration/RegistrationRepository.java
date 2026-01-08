package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;

import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RegistrationRepository {
    private static final List<Registration> registrations = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Default superadmin when the application starts
        Registration admin = new Registration();
        admin.setRegistrationId(idCounter.getAndIncrement());
        admin.setName("SuperAdmin");
        admin.setSurname("PO");
        admin.setEmail("superadmin@camping.nl");
        admin.setPassword(BCrypt.hashpw("Superadmin123!", BCrypt.gensalt()));
        admin.setVerified(true);

        // this user is superadmin
        admin.setRole("superadmin");

        registrations.add(admin);
    }

    public static List<Registration> getAll() {
        return registrations;
    }

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

    public static Registration findByVerificationToken(String token) {
        return registrations.stream()
                .filter(r -> r.getVerificationToken() != null && r.getVerificationToken().equals(token))
                .findFirst()
                .orElse(null);
    }

    public static Registration findByEmailAndPassword(String email, String password) {
        Registration found = registrations.stream()
                .filter(r -> r.getEmail() != null && r.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (found == null) return null;

        if (found.getPassword() == null || !BCrypt.checkpw(password, found.getPassword())) {
            return null;
        }

        return found;
    }

    public static Registration add(Registration registration) {
        if (registration.getRegistrationId() == 0) {
            registration.setRegistrationId(idCounter.getAndIncrement());
        }

        //  ensure this role is never null -> standard user
        if (registration.getRole() == null || registration.getRole().isBlank()) {
            registration.setRole("user");
        }

        // Hash the password before storing it
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

            // If role is given, overwrite
            if (updated.getRole() != null && !updated.getRole().isBlank()) {
                existing.setRole(updated.getRole());
            }

            if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
                existing.setPassword(BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt()));
            }
        }

        return existing;
    }

    public static boolean delete(int id) {
        return registrations.removeIf(r -> r.getRegistrationId() == id);
    }

    public static long countByRole(String role) {
        return registrations.stream()
                .filter(r -> r.getRole() != null && r.getRole().equalsIgnoreCase(role))
                .count();
    }

    public static List<Registration> findByRole(String role) {
        return registrations.stream()
                .filter(r -> r.getRole() != null && r.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }
}
