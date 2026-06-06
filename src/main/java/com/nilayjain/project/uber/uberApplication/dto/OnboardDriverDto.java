package com.nilayjain.project.uber.uberApplication.dto;

import lombok.Data;

@Data
public class OnboardDriverDto
{
    private String vehicleId;
    private Double currentLatitude;
    private Double currentLongitude;
}
