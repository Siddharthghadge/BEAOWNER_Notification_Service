// src/main/java/com/carrental/notification/service/EmailService.java
package com.carrental.notification.service;

import com.carrental.notification.dto.BookingNotificationRequest;
import com.carrental.notification.dto.VehicleVerificationRequest;
import jakarta.mail.MessagingException;

public interface EmailService {

    // CUSTOMER mail (with OTP)
    void sendBookingConfirmation(BookingNotificationRequest request) throws MessagingException;

    // OWNER mail (NO OTP)
    void sendOwnerBookingNotification(BookingNotificationRequest request) throws MessagingException;

    // com.carrental.notification.service.EmailService.java
    void sendVehicleVerificationStatus(VehicleVerificationRequest request) throws MessagingException;
    void sendPasswordResetEmail(String email, String token) throws MessagingException;
}
