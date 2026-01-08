package nl.hu.sd.s2.sds2project2025404finders.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

/**
 * Service for sending emails via Gmail SMTP.
 * 
 * Make sure to configure your Gmail credentials in email.properties file.
 * You need to use a Gmail App Password, not your regular password.
 * Get one from: https://myaccount.google.com/apppasswords
 */
public class EmailService {
    
    // Gmail SMTP settings
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    
    // These will be loaded from email.properties
    private static String fromEmail = "";
    private static String password = "";
    private static String baseUrl = "http://localhost:8080";
    
    // Load configuration from properties file
    static {
        loadConfiguration();
    }
    
    /**
     * Loads email configuration from properties file.
     */
    private static void loadConfiguration() {
        try {
            InputStream input = EmailService.class.getClassLoader()
                    .getResourceAsStream("email.properties");
            
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                
                // Load Gmail credentials
                fromEmail = props.getProperty("email.gmail.address", "").trim();
                password = props.getProperty("email.gmail.apppassword", "").trim().replaceAll("\\s+", ""); // Remove spaces
                
                // Load base URL for verification links
                String url = props.getProperty("email.base.url");
                if (url != null && !url.trim().isEmpty()) {
                    baseUrl = url.trim();
                }
                
                input.close();
                
                // Check if credentials are configured
                if (fromEmail.isEmpty() || password.isEmpty()) {
                    System.err.println("WARNING: Gmail credentials not configured in email.properties!");
                    System.err.println("Please set email.gmail.address and email.gmail.apppassword");
                } else {
                    System.out.println("Email configuration loaded successfully. From: " + fromEmail);
                }
            } else {
                System.err.println("WARNING: email.properties file not found!");
            }
        } catch (Exception e) {
            System.err.println("Error loading email configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Sends a verification email to a new user via Gmail SMTP.
     * 
     * @param toEmail The user's email address
     * @param name The user's first name
     * @param token The verification token
     * @return true if email was sent successfully, false otherwise
     */
    public static boolean sendVerificationEmail(String toEmail, String name, String token) {
        // Check if credentials are configured
        if (fromEmail.isEmpty() || password.isEmpty()) {
            System.err.println("ERROR: Gmail credentials not configured. Cannot send email.");
            System.err.println("Please configure email.gmail.address and email.gmail.apppassword in email.properties");
            
            // Print verification link to console as fallback
            String verificationLink = baseUrl + "/emailVerified.html?token=" + token;
            System.out.println("========================================");
            System.out.println("EMAIL NOT SENT - Gmail not configured");
            System.out.println("To: " + toEmail);
            System.out.println("Verification Link: " + verificationLink);
            System.out.println("========================================");
            return false;
        }
        
        try {
            // Create SMTP settings for Gmail
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", SMTP_HOST);
            mailProps.put("mail.smtp.port", SMTP_PORT);
            mailProps.put("mail.smtp.auth", "true"); // Authentication required
            mailProps.put("mail.smtp.starttls.enable", "true"); // Use TLS encryption
            mailProps.put("mail.smtp.starttls.required", "true");
            mailProps.put("mail.smtp.ssl.trust", SMTP_HOST); // Trust Gmail's SSL certificate
            
            // Create authenticator with Gmail credentials
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            
            // Create mail session with the settings and authenticator
            Session session = Session.getInstance(mailProps, authenticator);
            
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            
            // Set the sender
            message.setFrom(new InternetAddress(fromEmail));
            
            // Set the recipient
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            
            // Set the subject
            message.setSubject("Verifieer je e-mailadres - Camping Registratie");
            
            // Create the verification link - gebruik API endpoint die redirect naar frontend
            String verificationLink = baseUrl + "/sds2_project_2025_404_finders_war_exploded/api/verify?token=" + token;
            
            // Create the HTML content of the email in Dutch
            String htmlContent = "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color: #2c3e50;'>Welkom " + name + "!</h2>" +
                    "<p>Bedankt voor je registratie bij onze camping applicatie.</p>" +
                    "<p>Klik op de onderstaande link om je e-mailadres te verifiëren:</p>" +
                    "<p><a href='" + verificationLink + "' style='background-color: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>E-mailadres Verifiëren</a></p>" +
                    "<p>Of kopieer en plak deze link in je browser:</p>" +
                    "<p style='color: #7f8c8d; font-size: 12px; word-break: break-all;'>" + verificationLink + "</p>" +
                    "<p>Als je je niet hebt geregistreerd, negeer deze e-mail dan.</p>" +
                    "<p>Met vriendelijke groet,<br>P&amp;J Software</p>" +
                    "</body></html>";
            
            // Set the content as HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");
            
            // Send the email
            Transport.send(message);
            
            System.out.println("========================================");
            System.out.println("VERIFICATION EMAIL SENT VIA GMAIL");
            System.out.println("To: " + toEmail);
            System.out.println("Name: " + name);
            System.out.println("Verification Link: " + verificationLink);
            System.out.println("========================================");
            
            return true;
            
        } catch (MessagingException e) {
            System.err.println("Error sending verification email: " + e.getMessage());
            e.printStackTrace();
            
            // Print verification link to console as fallback
            String verificationLink = baseUrl + "/emailVerified.html?token=" + token;
            System.out.println("========================================");
            System.out.println("EMAIL FAILED - BUT HERE'S THE LINK:");
            System.out.println("To: " + toEmail);
            System.out.println("Verification Link: " + verificationLink);
            System.out.println("========================================");
            
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error sending email: " + e.getMessage());
            e.printStackTrace();
            
            // Print verification link to console as fallback
            String verificationLink = baseUrl + "/emailVerified.html?token=" + token;
            System.out.println("========================================");
            System.out.println("EMAIL FAILED - BUT HERE'S THE LINK:");
            System.out.println("To: " + toEmail);
            System.out.println("Verification Link: " + verificationLink);
            System.out.println("========================================");
            
            return false;
        }
    }
}


