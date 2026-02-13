// com.carrental.notification.dto.VehicleVerificationRequest.java
package com.carrental.notification.dto;

import lombok.Data;

@Data
public class VehicleVerificationRequest {
    private String ownerEmail;
    private String ownerName;
    private String vehicleBrand;
    private String vehicleModel;
    private String status; // "APPROVED" or "REJECTED"
    private String reason; // Optional: Why it was rejected
}