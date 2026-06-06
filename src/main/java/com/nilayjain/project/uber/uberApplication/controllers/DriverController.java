package com.nilayjain.project.uber.uberApplication.controllers;

import com.nilayjain.project.uber.uberApplication.dto.*;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import com.nilayjain.project.uber.uberApplication.services.DriverService;
import com.nilayjain.project.uber.uberApplication.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@Secured("ROLE_DRIVER")
public class DriverController {
    private final DriverService driverService;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(withoutOtp(driverService.acceptRide(rideRequestId)));
    }

    @GetMapping("/rideRequests")
    public ResponseEntity<Page<RideRequestDto>> getPendingRideRequests(@RequestParam(defaultValue = "0")Integer pageOffset,
                                                                       @RequestParam(defaultValue = "10",required = false)Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
                Sort.by(Sort.Direction.DESC, "requestedTime", "id"));
        return ResponseEntity.ok(rideRequestService
                .findRideRequestsByStatus(RideRequestStatus.PENDING, pageRequest)
                .map(rideRequest -> modelMapper.map(rideRequest, RideRequestDto.class)));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                              @RequestBody RideStartDto rideStartDto){
        return ResponseEntity.ok(withoutOtp(driverService.startRide(rideRequestId ,rideStartDto.getOtp())));
    }
    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(withoutOtp(driverService.endRide(rideId)));
    }


    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide (@PathVariable Long rideId){
        return ResponseEntity.ok(withoutOtp(driverService.cancelRide(rideId)));
    }
    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId(),ratingDto.getRating()));
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>>getAllMyRides(@RequestParam(defaultValue = "0")Integer pageOffset,
                                                      @RequestParam(defaultValue = "10",required = false)Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageOffset,pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest).map(this::withoutOtp));
    }

    private RideDto withoutOtp(RideDto rideDto) {
        rideDto.setOtp(null);
        return rideDto;
    }

}
