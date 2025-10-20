package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.exceptions.ResourceNotFoundException;
import com.nilayjain.project.uber.uberApplication.repositories.RideRequestRepository;
import com.nilayjain.project.uber.uberApplication.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;



    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(()-> new ResourceNotFoundException("RideRequest not found with id:"+ rideRequestId));
    }
    @Override
    public void update(RideRequest rideRequest) {
     rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(()-> new ResourceNotFoundException("RideRequest not found with id:"+ rideRequest.getId()) );
     rideRequestRepository.save(rideRequest);
    }
}
