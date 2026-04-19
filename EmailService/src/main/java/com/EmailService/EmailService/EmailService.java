package com.EmailService.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }


    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(mimeMessage);
    }


    public void sendOrderConfirmation(String to, Long  orderId, double amount) {
        String subject = "Order Confirmation - " + orderId;

        String html = """
                <html>
                    <body style="font-family: Arial, sans-serif;">
                        <h2>Thank you for your order!</h2>
                        <p>Your order has been successfully placed.</p>
                        <p><strong>Order ID:</strong> %s</p>
                        <p><strong>Amount Paid:</strong> ₹%.2f</p>
                        <br>
                        <p>We will notify you once your order is shipped.</p>
                        <br>
                        <p>Regards,<br>Your Store Team</p>
                    </body>
                </html>
                """.formatted(orderId, amount);

        try {
            sendHtmlEmail(to, subject, html);
        } catch (Exception e) {
            // fallback to simple text email
            sendSimpleEmail(to, subject,
                    "Your order " + orderId + " has been placed.\nAmount: ₹" + amount);
        }
    }

}
