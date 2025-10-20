package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RideDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {
    // methods
    RideDto acceptRide(Long rideRequestId);
    RideDto cancelRide(Long rideId);
    RideDto startRide(Long rideId , String otp);
    RideDto endRide(Long rideId);
    RiderDto rateRider(Long rideId, Integer rating);
    DriverDto getMyProfile();
    Page<RideDto> getAllMyRides(PageRequest pageRequest);
    Driver getCurrentDriver();
    Driver updateDriverAvailability(Driver driver, boolean available);
    Driver createNewDriver(Driver driver);

}
