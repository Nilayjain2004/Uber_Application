<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.Rider;

public interface RatingService {
    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);
    void createNewRating(Ride ride);
}
=======
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.RiderDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.Rider;

public interface RatingService {
    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);
    void createNewRating(Ride ride);
}
>>>>>>> master
