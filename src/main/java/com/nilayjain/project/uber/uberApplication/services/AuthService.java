package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.DriverSignupDto;
import com.nilayjain.project.uber.uberApplication.dto.OnboardDriverDto;
import com.nilayjain.project.uber.uberApplication.dto.SignupDto;
import com.nilayjain.project.uber.uberApplication.dto.UserDto;

public interface AuthService {
    //methods
String[] login(String email,String password);
UserDto signup(SignupDto signupDto);
DriverDto signupDriver(DriverSignupDto driverSignupDto);
DriverDto onboardNewDriver(Long userId, OnboardDriverDto onboardDriverDto);
String refreshToken(String refreshToken);
}
