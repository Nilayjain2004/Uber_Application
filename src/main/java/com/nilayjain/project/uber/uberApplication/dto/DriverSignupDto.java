package com.nilayjain.project.uber.uberApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverSignupDto {
    private String name;
    private String email;
    private String password;
    private String vehicleId;
    private Double currentLatitude;
    private Double currentLongitude;
}
