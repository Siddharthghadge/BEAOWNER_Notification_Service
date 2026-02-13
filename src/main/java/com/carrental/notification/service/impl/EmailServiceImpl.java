package com.carrental.notification.service.impl;

import com.carrental.notification.dto.BookingNotificationRequest;
import com.carrental.notification.dto.VehicleVerificationRequest;
import com.carrental.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // ================= CUSTOMER EMAIL =================
    @Override
    public void sendBookingConfirmation(BookingNotificationRequest request) throws MessagingException {

        Context context = new Context();
        context.setVariable("name", request.getCustomerName());
        context.setVariable("car", request.getCarModel());
        context.setVariable("location", request.getPickupLocation());
        context.setVariable("phone", request.getOwnerPhone());
        context.setVariable("otp", request.getPickupOtp());
        context.setVariable("type", request.getBookingType());

        String htmlContent = templateEngine.process("booking-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setSubject("SwiftRide: Booking Confirmed - " + request.getCarModel());
        helper.setText(htmlContent, true);
        helper.setTo(request.getCustomerEmail());

        mailSender.send(mimeMessage);
    }

    // ================= OWNER EMAIL (NO OTP) =================
    @Override
    public void sendOwnerBookingNotification(BookingNotificationRequest request) throws MessagingException {

        Context context = new Context();
        context.setVariable("customerName", request.getCustomerName());
        context.setVariable("car", request.getCarModel());
        context.setVariable("location", request.getPickupLocation());
        context.setVariable("type", request.getBookingType());

        String htmlContent = templateEngine.process("owner-booking-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setSubject("SwiftRide: New Booking Received - " + request.getCarModel());
        helper.setText(htmlContent, true);
        helper.setTo(request.getOwnerEmail());

        mailSender.send(mimeMessage);
    }


    // com.carrental.notification.service.impl.EmailServiceImpl.java

    @Override
    public void sendVehicleVerificationStatus(VehicleVerificationRequest request) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", request.getOwnerName());
        context.setVariable("brand", request.getVehicleBrand());
        context.setVariable("model", request.getVehicleModel());
        context.setVariable("status", request.getStatus());
        context.setVariable("reason", request.getReason());

        // You can use one template and use thymeleaf th:if for Approved/Rejected logic
        String htmlContent = templateEngine.process("vehicle-verification-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String subject = request.getStatus().equalsIgnoreCase("APPROVED")
                ? "Congratulations! Your vehicle is now live"
                : "Update regarding your vehicle listing";

        helper.setSubject("SwiftRide: " + subject);
        helper.setText(htmlContent, true);
        helper.setTo(request.getOwnerEmail());

        mailSender.send(mimeMessage);
    }

    // ================= PASSWORD RESET EMAIL =================
    @Override
    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        Context context = new Context();
        context.setVariable("token", token);

        // Process the HTML template
        String htmlContent = templateEngine.process("password-reset-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setSubject("SwiftRide: Password Reset Code");
        helper.setText(htmlContent, true); // true indicates HTML
        helper.setTo(email);

        mailSender.send(mimeMessage);
    }
}
