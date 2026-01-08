package nl.hu.sd.s2.sds2project2025404finders.service;

import java.util.UUID;

/**
 * Service for generating and validating verification tokens.
 * 
 * This class creates unique tokens that are hard to guess.
 * We use UUID (Universally Unique Identifier) which creates random, unique strings.
 */
public class VerificationTokenService {

    /**
     * Generates a unique verification token.
     *
     * @return A unique token string (example: "550e8400-e29b-41d4-a716-446655440000")
     */
    public static String generateToken() {
        // UUID.randomUUID() creates a random unique identifier
        // .toString() converts it to a string we can use
        return UUID.randomUUID().toString();
    }
}
