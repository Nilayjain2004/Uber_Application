<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.strategies.impl;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.services.DistanceService;
import com.nilayjain.project.uber.uberApplication.strategies.RideFareCalculationStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategies {
    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return  distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }
}
=======
package com.nilayjain.project.uber.uberApplication.strategies.impl;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.services.DistanceService;
import com.nilayjain.project.uber.uberApplication.strategies.RideFareCalculationStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategies {
    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return  distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }
}
>>>>>>> master
