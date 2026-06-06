package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RideRequestService {
    RideRequest findRideRequestById(Long rideRequestId);

    void update (RideRequest rideRequest);

    Page<RideRequest> findRideRequestsByStatus(RideRequestStatus rideRequestStatus, Pageable pageable);
}
