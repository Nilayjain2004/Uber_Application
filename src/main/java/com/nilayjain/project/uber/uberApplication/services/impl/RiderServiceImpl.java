package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RideDto;
import com.nilayjain.project.uber.uberApplication.dto.RideRequestDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.*;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideStatus;
import com.nilayjain.project.uber.uberApplication.exceptions.ResourceNotFoundException;
import com.nilayjain.project.uber.uberApplication.repositories.RideRequestRepository;
import com.nilayjain.project.uber.uberApplication.repositories.RiderRepository;
import com.nilayjain.project.uber.uberApplication.services.DriverService;
import com.nilayjain.project.uber.uberApplication.services.RatingService;
import com.nilayjain.project.uber.uberApplication.services.RideService;
import com.nilayjain.project.uber.uberApplication.services.RiderService;
import com.nilayjain.project.uber.uberApplication.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {
    // Methods implementation

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;
    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare  = rideStrategyManager.rideFareCalculationStrategies().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager
                .driverMatchingStrategies(rider.getRating()).findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest ,RideRequestDto.class);

    }
    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own the this ride with id: "+rideId);
        }

        if(ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled ,invalid status:"+ride.getRideStatus());
        }

        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider=getCurrentRider();

        if(!rider.equals(ride.getDriver())){
            throw new RuntimeException("Rider is not owner of this ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDES)){
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status:" +ride.getRideStatus());
        }

        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRider(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
       return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()
        ));
    }
}
