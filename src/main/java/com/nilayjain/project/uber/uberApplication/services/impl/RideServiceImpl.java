package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.dto.RideRequestDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentMethod;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideStatus;
import com.nilayjain.project.uber.uberApplication.exceptions.ResourceNotFoundException;
import com.nilayjain.project.uber.uberApplication.repositories.RideRepository;
import com.nilayjain.project.uber.uberApplication.repositories.RiderRepository;
import com.nilayjain.project.uber.uberApplication.services.RideRequestService;
import com.nilayjain.project.uber.uberApplication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RiderRepository riderRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final RideRepository rideRepository;

    // Methods implementation
    @Override
    public Ride getRideById(Long rideId) {

        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id:"+rideId));
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus((RideRequestStatus.CONFIRMED));
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setRider(rideRequest.getRider());
        ride.setFare(rideRequest.getFare());
        ride.setPaymentMethod(rideRequest.getPaymentMethod() == null ? PaymentMethod.CASH : rideRequest.getPaymentMethod());
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {

        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider ,pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver ,pageRequest);
    }

    private String generateRandomOTP(){
        Random random =new Random();
        int otpInt = random.nextInt(10000);
        return String.format("%04d",otpInt);
    }
}
