package nl.hu.sd.s2.sds2project2025404finders;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

// Class for everything related to creating JWT tokens
public class JwtHelper {

    // One secret key for the whole backend to sign and verify tokens
    public static final SecretKey KEY = Jwts.SIG.HS256.key().build();

    // Creates a signed JWT with the given user identifier (subject) and role
    public static String createToken(String subject, String role) {
        // Start from current time
        Calendar cal = Calendar.getInstance();

        // Token expires after 30 minutes
        cal.add(Calendar.MINUTE, 30);
        Date exp = cal.getTime();

        return Jwts.builder()
                .subject(subject)                  // who is the user
                .expiration(exp)                   // endtime
                .claims(Map.of("role", role))      // extra info (like role)
                .signWith(KEY)                     // sign with secret key
                .compact();                        // turn it into String token
    }
}