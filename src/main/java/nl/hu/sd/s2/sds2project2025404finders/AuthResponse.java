package nl.hu.sd.s2.sds2project2025404finders;

public class AuthResponse {
    public boolean ok;
    public String message;
    public String token; // optional demo token

    public AuthResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
        this.token = null;
    }
}
