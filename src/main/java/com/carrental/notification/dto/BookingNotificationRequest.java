package com.carrental.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingNotificationRequest {
    @JsonProperty("customerEmail")
    private String customerEmail;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("ownerName")
    private String ownerName;

    private String ownerEmail;

    @JsonProperty("ownerPhone")
    private String ownerPhone;

    @JsonProperty("carModel") // This was the mismatch!
    private String carModel;

    @JsonProperty("pickupLocation")
    private String pickupLocation;

    @JsonProperty("pickupOtp")
    private String pickupOtp;

    @JsonProperty("bookingType")
    private String bookingType;
}