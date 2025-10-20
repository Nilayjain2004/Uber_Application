<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RideDto;
import com.nilayjain.project.uber.uberApplication.dto.RideRequestDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService  {
    // methods

    RideRequestDto requestRide(RideRequestDto rideRequestDto) ;
    RideDto cancelRide(Long rideId) ;
    DriverDto rateDriver(Long rideId, Integer rating);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRider(PageRequest pageRequest);
    Rider createNewRider(User user);
    Rider getCurrentRider();
}
=======
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RideDto;
import com.nilayjain.project.uber.uberApplication.dto.RideRequestDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService  {
    // methods

    RideRequestDto requestRide(RideRequestDto rideRequestDto) ;
    RideDto cancelRide(Long rideId) ;
    DriverDto rateDriver(Long rideId, Integer rating);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRider(PageRequest pageRequest);
    Rider createNewRider(User user);
    Rider getCurrentRider();
}
>>>>>>> master
