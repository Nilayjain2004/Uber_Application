package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.RideRequestDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {
    //methods
    Ride getRideById(Long rideId);
    Ride createNewRide(RideRequest rideRequest, Driver Driver);
    Ride updateRideStatus(Ride ride, RideStatus rideStatus);
    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);
    Page<Ride> getAllRidesOfDriver(Driver driver , PageRequest pageRequest);
}
