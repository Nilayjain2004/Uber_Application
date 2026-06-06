package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    // jpa provides CURD operation without sql without writing it
    // RideRequest is a Entity
    // Long is the type of the primary key of the RideRequest Entity @ID
    Page<RideRequest> findByRideRequestStatus(RideRequestStatus rideRequestStatus, Pageable pageable);
}
