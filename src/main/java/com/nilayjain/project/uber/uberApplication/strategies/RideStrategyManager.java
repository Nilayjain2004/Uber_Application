<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.strategies;

import com.nilayjain.project.uber.uberApplication.strategies.impl.DriverMatchingFHighestRatedDriverStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingFHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    public DriverMatchingStrategies driverMatchingStrategies(double riderRating) {
        if(riderRating >= 4.8 ){
            return highestRatedDriverStrategy;
        }
        else{
           return nearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategies rideFareCalculationStrategies(){
        //peak hour 6 pm to 9 pm
        LocalTime surgeStartTime =LocalTime.of(18,0);
        LocalTime surgeEndTime =LocalTime.of(21,0);
        LocalTime currentTime =LocalTime.now();

        boolean isSurgeTime =currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }
        else {
            return defaultFareCalculationStrategy;
        }
    }

}
=======
package com.nilayjain.project.uber.uberApplication.strategies;

import com.nilayjain.project.uber.uberApplication.strategies.impl.DriverMatchingFHighestRatedDriverStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingFHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    public DriverMatchingStrategies driverMatchingStrategies(double riderRating) {
        if(riderRating >= 4.8 ){
            return highestRatedDriverStrategy;
        }
        else{
           return nearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategies rideFareCalculationStrategies(){
        //peak hour 6 pm to 9 pm
        LocalTime surgeStartTime =LocalTime.of(18,0);
        LocalTime surgeEndTime =LocalTime.of(21,0);
        LocalTime currentTime =LocalTime.now();

        boolean isSurgeTime =currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }
        else {
            return defaultFareCalculationStrategy;
        }
    }

}
>>>>>>> master
