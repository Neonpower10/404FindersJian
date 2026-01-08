package nl.hu.sd.s2.sds2project2025404finders.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import nl.hu.sd.s2.sds2project2025404finders.domain.Booking;
import nl.hu.sd.s2.sds2project2025404finders.domain.BreadOrder;
import nl.hu.sd.s2.sds2project2025404finders.domain.Reservation;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
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

    public static boolean sendBookingConfirmation(Booking booking) {

        if (fromEmail.isEmpty() || password.isEmpty()) {
            System.err.println("ERROR: Gmail credentials not configured. Cannot send booking email.");
            return false;
        }

        try {
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", SMTP_HOST);
            mailProps.put("mail.smtp.port", SMTP_PORT);
            mailProps.put("mail.smtp.auth", "true");
            mailProps.put("mail.smtp.starttls.enable", "true");
            mailProps.put("mail.smtp.starttls.required", "true");
            mailProps.put("mail.smtp.ssl.trust", SMTP_HOST);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(mailProps, authenticator);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(booking.getEmail()));

            message.setSubject("Bevestiging van je boeking");

            String htmlContent =
                    "<html><body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color:#2c3e50;'>Je boeking is bevestigd!</h2>" +
                            "<p>Hieronder vind je een overzicht van jouw gegevens:</p>" +

                            "<h3>Persoonsgegevens</h3>" +
                            "<ul>" +
                            "<li><strong>Naam:</strong> " + booking.getPersonFirstName() + " " + booking.getPersonLastName() + "</li>" +
                            "<li><strong>Geslacht:</strong> " + (booking.getGender() != null ? booking.getGender() : "-") + "</li>" +
                            "<li><strong>Email:</strong> " + booking.getEmail() + "</li>" +
                            "<li><strong>Telefoon:</strong> " + booking.getPhoneNumber() + "</li>" +
                            "</ul>" +

                            "<h3>Adres</h3>" +
                            "<ul>" +
                            "<li>" + booking.getStreetName() + " " + booking.getHouseNumber() + "</li>" +
                            "<li>" + booking.getPostcode() + " " + booking.getPlace() + "</li>" +
                            "<li>" + booking.getCountry() + "</li>" +
                            "</ul>" +

                            "<h3>Boekingsinformatie</h3>" +
                            "<ul>" +
                            "<li><strong>Soort plek:</strong> " + booking.getCampEq() + "</li>" +
                            "<li><strong>Aankomst:</strong> " + booking.getStartDate() + "</li>" +
                            "<li><strong>Vertrek:</strong> " + booking.getEndDate() + "</li>" +
                            "<li><strong>Aangemaakt op:</strong> " + booking.getCreationDate() + "</li>" +
                            "<li><strong>Aantal personen:</strong> " + booking.getAmountPersons() + "</li>" +
                            "</ul>" +

                            "<br><p>Met vriendelijke groet,<br>P&amp;J Software</p>" +
                            "</body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("BOOKING CONFIRMATION EMAIL SENT to " + booking.getEmail());
            return true;

        } catch (Exception e) {
            System.err.println("Error sending booking confirmation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sends a confirmation email for a bread order.
     * 
     * @param order The bread order that was placed
     * @return true if email was sent successfully, false otherwise
     */
    public static boolean sendBreadOrderConfirmation(BreadOrder order) {
        // Check if email is provided
        if (order.getEmail() == null || order.getEmail().trim().isEmpty()) {
            System.out.println("No email address provided for bread order " + order.getOrderId() + ". Skipping email.");
            return false;
        }

        if (fromEmail.isEmpty() || password.isEmpty()) {
            System.err.println("ERROR: Gmail credentials not configured. Cannot send bread order email.");
            return false;
        }

        try {
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", SMTP_HOST);
            mailProps.put("mail.smtp.port", SMTP_PORT);
            mailProps.put("mail.smtp.auth", "true");
            mailProps.put("mail.smtp.starttls.enable", "true");
            mailProps.put("mail.smtp.starttls.required", "true");
            mailProps.put("mail.smtp.ssl.trust", SMTP_HOST);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(mailProps, authenticator);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(order.getEmail()));

            message.setSubject("Bevestiging van je broodbestelling");

            // Parse order items from JSON string
            StringBuilder itemsHtml = new StringBuilder();
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> orderItems = mapper.readValue(
                    order.getOrderItems(),
                    new TypeReference<List<Map<String, Object>>>() {}
                );
                
                itemsHtml.append("<table style='border-collapse: collapse; width: 100%; margin: 20px 0;'>");
                itemsHtml.append("<tr style='background-color: #f2f2f2;'>");
                itemsHtml.append("<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Broodje</th>");
                itemsHtml.append("<th style='padding: 10px; text-align: center; border: 1px solid #ddd;'>Aantal</th>");
                itemsHtml.append("<th style='padding: 10px; text-align: right; border: 1px solid #ddd;'>Prijs per stuk</th>");
                itemsHtml.append("<th style='padding: 10px; text-align: right; border: 1px solid #ddd;'>Subtotaal</th>");
                itemsHtml.append("</tr>");
                
                for (Map<String, Object> item : orderItems) {
                    String breadName = (String) item.get("breadName");
                    int quantity = ((Number) item.get("quantity")).intValue();
                    double price = ((Number) item.get("price")).doubleValue();
                    double subtotal = price * quantity;
                    
                    itemsHtml.append("<tr>");
                    itemsHtml.append("<td style='padding: 10px; border: 1px solid #ddd;'>").append(breadName).append("</td>");
                    itemsHtml.append("<td style='padding: 10px; text-align: center; border: 1px solid #ddd;'>").append(quantity).append("</td>");
                    itemsHtml.append("<td style='padding: 10px; text-align: right; border: 1px solid #ddd;'>€").append(String.format("%.2f", price)).append("</td>");
                    itemsHtml.append("<td style='padding: 10px; text-align: right; border: 1px solid #ddd;'>€").append(String.format("%.2f", subtotal)).append("</td>");
                    itemsHtml.append("</tr>");
                }
                
                itemsHtml.append("</table>");
            } catch (Exception e) {
                itemsHtml.append("<p>Er is een fout opgetreden bij het weergeven van de bestelde items.</p>");
                System.err.println("Error parsing order items: " + e.getMessage());
            }

            String htmlContent =
                    "<html><body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color:#2c3e50;'>Je broodbestelling is bevestigd!</h2>" +
                            "<p>Bedankt voor je bestelling. Hieronder vind je een overzicht van je bestelling:</p>" +

                            "<h3>Bestellinggegevens</h3>" +
                            "<ul>" +
                            "<li><strong>Bestelnummer:</strong> " + order.getOrderId() + "</li>" +
                            "<li><strong>Datum:</strong> " + order.getOrderDate() + "</li>" +
                            "<li><strong>Naam:</strong> " + order.getCustomerFirstName() + " " + order.getCustomerLastName() + "</li>" +
                            "<li><strong>Telefoon:</strong> " + order.getPhoneNumber() + "</li>" +
                            "</ul>" +

                            "<h3>Bestelde items</h3>" +
                            itemsHtml.toString() +

                            "<p style='text-align: right; font-size: 18px; font-weight: bold; margin-top: 20px;'>" +
                            "Totaal: €" + String.format("%.2f", order.getTotalPrice()) +
                            "</p>" +

                            "<br><p>Met vriendelijke groet,<br>P&amp;J Software</p>" +
                            "</body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("BREAD ORDER CONFIRMATION EMAIL SENT to " + order.getEmail());
            return true;

        } catch (Exception e) {
            System.err.println("Error sending bread order confirmation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sends a confirmation email for a product reservation/rental.
     * 
     * @param reservation The product reservation that was made
     * @param product The product that was reserved
     * @param customerEmail The email address of the customer (optional)
     * @return true if email was sent successfully, false otherwise
     */
    public static boolean sendProductReservationConfirmation(Reservation reservation, Product product, String customerEmail) {
        // Check if email is provided
        if (customerEmail == null || customerEmail.trim().isEmpty()) {
            System.out.println("No email address provided for reservation " + reservation.getReservationId() + ". Skipping email.");
            return false;
        }

        if (fromEmail.isEmpty() || password.isEmpty()) {
            System.err.println("ERROR: Gmail credentials not configured. Cannot send product reservation email.");
            return false;
        }

        try {
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", SMTP_HOST);
            mailProps.put("mail.smtp.port", SMTP_PORT);
            mailProps.put("mail.smtp.auth", "true");
            mailProps.put("mail.smtp.starttls.enable", "true");
            mailProps.put("mail.smtp.starttls.required", "true");
            mailProps.put("mail.smtp.ssl.trust", SMTP_HOST);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(mailProps, authenticator);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));

            message.setSubject("Bevestiging van je productreservering");

            // Calculate total price
            java.math.BigDecimal totalPrice = reservation.calculatePrice();

            String htmlContent =
                    "<html><body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color:#2c3e50;'>Je productreservering is bevestigd!</h2>" +
                            "<p>Bedankt voor je reservering. Hieronder vind je een overzicht van je reservering:</p>" +

                            "<h3>Reserveringsgegevens</h3>" +
                            "<ul>" +
                            "<li><strong>Reserveringsnummer:</strong> " + reservation.getReservationId() + "</li>" +
                            "<li><strong>Naam:</strong> " + reservation.getCustomerName() + "</li>" +
                            "</ul>" +

                            "<h3>Productinformatie</h3>" +
                            "<ul>" +
                            "<li><strong>Product:</strong> " + product.getProductName() + "</li>" +
                            "<li><strong>Aantal:</strong> " + reservation.getProductAmount() + "</li>" +
                            "<li><strong>Prijs per dag:</strong> €" + reservation.getPricePerDay() + "</li>" +
                            "</ul>" +

                            "<h3>Periode</h3>" +
                            "<ul>" +
                            "<li><strong>Startdatum:</strong> " + reservation.getReservationStartDate() + "</li>" +
                            "<li><strong>Einddatum:</strong> " + reservation.getReservationEndDate() + "</li>" +
                            "</ul>" +

                            "<p style='text-align: right; font-size: 18px; font-weight: bold; margin-top: 20px;'>" +
                            "Totaal: €" + totalPrice +
                            "</p>" +

                            "<br><p>Met vriendelijke groet,<br>P&amp;J Software</p>" +
                            "</body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("PRODUCT RESERVATION CONFIRMATION EMAIL SENT to " + customerEmail);
            return true;

        } catch (Exception e) {
            System.err.println("Error sending product reservation confirmation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}


