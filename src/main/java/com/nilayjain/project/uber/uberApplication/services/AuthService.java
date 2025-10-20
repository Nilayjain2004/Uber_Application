<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.SignupDto;
import com.nilayjain.project.uber.uberApplication.dto.UserDto;

public interface AuthService {
    //methods
String[] login(String email,String password);
UserDto signup(SignupDto signupDto);
DriverDto onboardNewDriver(Long userId,String vehicleId);
String refreshToken(String refreshToken);
}
=======
package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.SignupDto;
import com.nilayjain.project.uber.uberApplication.dto.UserDto;

public interface AuthService {
    //methods
String[] login(String email,String password);
UserDto signup(SignupDto signupDto);
DriverDto onboardNewDriver(Long userId,String vehicleId);
String refreshToken(String refreshToken);
}
>>>>>>> master
