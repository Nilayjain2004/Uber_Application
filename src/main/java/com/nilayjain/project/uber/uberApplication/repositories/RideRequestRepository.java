<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    // jpa provides CURD operation without sql without writing it
    // RideRequest is a Entity
    // Long is the type of the primary key of the RideRequest Entity @ID
}
=======
package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    // jpa provides CURD operation without sql without writing it
    // RideRequest is a Entity
    // Long is the type of the primary key of the RideRequest Entity @ID
}
>>>>>>> master
