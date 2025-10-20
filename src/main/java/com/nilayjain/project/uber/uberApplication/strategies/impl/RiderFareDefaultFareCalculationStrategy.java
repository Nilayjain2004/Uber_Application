package com.nilayjain.project.uber.uberApplication.strategies.impl;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.services.DistanceService;
import com.nilayjain.project.uber.uberApplication.strategies.RideFareCalculationStrategies;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategies {
    private final DistanceService distanceService;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return  distance * RIDE_FARE_MULTIPLIER;
    }
}
