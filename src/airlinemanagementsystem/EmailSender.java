package airlinemanagementsystem;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String body, String attachmentPath) {
        String from = "harshflight8@gmail.com";
        String password = "123456789@A";

        // Setup mail server properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create a session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            // Create the message part with the body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create the attachment part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(attachmentPath);

            // Create multipart and add both parts (body and attachment)
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the multipart content to the message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String recipient = args[0]; // Get recipient from command-line argument
        String subject = args[1]; // Get subject from command-line argument
        String body = args[2]; // Get email body from command-line argument
        String attachmentPath = args[3]; // Get attachment path from command-line argument

        sendEmail(recipient, subject, body, attachmentPath);
    }
}
