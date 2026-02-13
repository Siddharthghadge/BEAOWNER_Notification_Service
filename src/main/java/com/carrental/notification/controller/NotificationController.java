package com.carrental.notification.controller;

import com.carrental.notification.dto.BookingNotificationRequest;
import com.carrental.notification.dto.NotificationRequest;
import com.carrental.notification.dto.VehicleVerificationRequest;
import com.carrental.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// src/main/java/com/carrental/notification/controller/NotificationController.java
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    // Injecting the Interface
    @Autowired
    private EmailService emailService;

    @PostMapping("/booking-success")
    public ResponseEntity<String> notifyBooking(@RequestBody BookingNotificationRequest request) {

        try {
            // CUSTOMER MAIL
            emailService.sendBookingConfirmation(request);

            // OWNER MAIL (only if owner email exists)
            if (request.getOwnerEmail() != null) {
                emailService.sendOwnerBookingNotification(request);
            }

            return ResponseEntity.ok("Customer & Owner emails sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Email sending failed: " + e.getMessage());
        }
    }


    // com.carrental.notification.controller.NotificationController.java

    @PostMapping("/vehicle-verification")
    public ResponseEntity<String> notifyVehicleVerification(@RequestBody VehicleVerificationRequest request) {
        try {
            emailService.sendVehicleVerificationStatus(request);
            return ResponseEntity.ok("Verification email sent to owner successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Verification email failed: " + e.getMessage());
        }
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendGenericNotification(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String message = request.get("message");

            // For Forgot Password, we extract the code from the message or pass it directly.
            // Since your Auth Service sends: "Your password reset code is: 123456"
            // Let's make it cleaner by checking if it's a reset request.

            if (message.contains("reset code")) {
                // Extract just the 6 digits from the string
                String otp = message.replaceAll("[^0-9]", "");
                emailService.sendPasswordResetEmail(email, otp);
            }

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}