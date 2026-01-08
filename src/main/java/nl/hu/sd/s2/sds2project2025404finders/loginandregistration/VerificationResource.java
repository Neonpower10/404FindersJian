package nl.hu.sd.s2.sds2project2025404finders.loginandregistration;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Registration;

/**
 * REST endpoint for verifying email addresses.
 * 
 * When a user clicks the verification link in the email,
 * this endpoint is called with the verification token.
 */
@Path("/verify")
@Produces(MediaType.APPLICATION_JSON)
public class VerificationResource {
    
    /**
     * Verifies a user based on a verification token.
     * 
     * @param token The verification token from the email link
     * @return Response with success or error message
     */
    @GET
    public Response verifyEmail(@QueryParam("token") String token) {
        // Check if a token was provided
        if (token == null || token.trim().isEmpty()) {
            return htmlResponse("Geen token", "De verificatielink is niet geldig.", false);
        }
        
        // Find the user with this token
        Registration registration = RegistrationRepository.findByVerificationToken(token);
        
        if (registration == null) {
            return htmlResponse("Token niet gevonden", "De verificatielink is niet geldig of al gebruikt.", false);
        }
        
        // If the user is already verified
        if (registration.isVerified()) {
            return htmlResponse("Al geverifieerd", "Dit e-mailadres is al geverifieerd.", true);
        }
        
        // Verify the user
        registration.setVerified(true);
        
        return htmlResponse("Geverifieerd!", "Je e-mail is succesvol geverifieerd! Je kunt nu inloggen.", true);
    }
    
    private Response htmlResponse(String title, String message, boolean success) {
        String color = success ? "#27ae60" : "#e74c3c";
        String icon = success ? "✓" : "✗";
        String html = "<!DOCTYPE html>" +
                "<html lang='nl'>" +
                "<head>" +
                "<meta charset='UTF-8'/>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>" + title + "</title>" +
                "<link rel='stylesheet' href='http://localhost:5173/public/styles/style.css'>" +
                "<link rel='stylesheet' href='http://localhost:5173/public/styles/nav.css'>" +
                "<link rel='stylesheet' href='http://localhost:5173/public/styles/footer.css'>" +
                "<link rel='stylesheet' href='http://localhost:5173/public/styles/login.css'>" +
                "<style>" +
                ".success-container{max-width:500px;margin:50px auto;padding:30px;text-align:center;background:white;border-radius:10px;box-shadow:0 2px 10px rgba(0,0,0,0.1)}" +
                ".success-icon{font-size:64px;color:" + color + ";margin-bottom:20px}" +
                ".login-button{display:inline-block;margin-top:20px;padding:12px 30px;background-color:#3498db;color:white;text-decoration:none;border-radius:5px;transition:background-color 0.3s}" +
                ".login-button:hover{background-color:#2980b9}" +
                "</style>" +
                "</head>" +
                "<body class='auth-body'>" +
                "<header id='site-header'></header>" +
                "<main class='auth-main'>" +
                "<section class='login-container'>" +
                "<div class='success-container'>" +
                "<div class='success-icon'>" + icon + "</div>" +
                "<h2 class='success-message'>" + title + "</h2>" +
                "<p>" + message + "</p>" +
                "<a href='http://localhost:5173/accountLogin.html' class='login-button'>Naar Inloggen</a>" +
                "</div>" +
                "</section>" +
                "</main>" +
                "<footer id='site-footer'></footer>" +
                "<script type='module' src='http://localhost:5173/src/layout/layout.js'></script>" +
                "<script type='module' src='http://localhost:5173/src/design/themeLoader.js'></script>" +
                "</body>" +
                "</html>";
        
        return Response.ok(html).type("text/html").build();
    }
}

