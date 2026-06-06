package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.dto.DriverDto;
import com.nilayjain.project.uber.uberApplication.dto.DriverSignupDto;
import com.nilayjain.project.uber.uberApplication.dto.OnboardDriverDto;
import com.nilayjain.project.uber.uberApplication.dto.PointDto;
import com.nilayjain.project.uber.uberApplication.dto.SignupDto;
import com.nilayjain.project.uber.uberApplication.dto.UserDto;
import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.User;
import com.nilayjain.project.uber.uberApplication.entities.enums.Role;
import com.nilayjain.project.uber.uberApplication.exceptions.ResourceNotFoundException;
import com.nilayjain.project.uber.uberApplication.exceptions.RuntimeConflictException;
import com.nilayjain.project.uber.uberApplication.repositories.UserRepository;
import com.nilayjain.project.uber.uberApplication.security.JWTService;
import com.nilayjain.project.uber.uberApplication.services.AuthService;
import com.nilayjain.project.uber.uberApplication.services.DriverService;
import com.nilayjain.project.uber.uberApplication.services.RiderService;
import com.nilayjain.project.uber.uberApplication.services.WalletService;
import com.nilayjain.project.uber.uberApplication.utils.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    // Methods implementation
    @Override
    public String[] login(String email, String password) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

       User user = (User) authentication.getPrincipal();
       String accessToken = jwtService.generateAccessToken(user);
       String refreshToken = jwtService.generateRefreshToken(user);
        return new String[]{accessToken , refreshToken};
    }
    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser= userRepository.save(mappedUser);

        //create user related entities
        riderService.createNewRider((savedUser));
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    @Transactional
    public DriverDto signupDriver(DriverSignupDto driverSignupDto) {
        User user = userRepository.findByEmail(driverSignupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+driverSignupDto.getEmail());

        if(driverSignupDto.getCurrentLatitude() == null || driverSignupDto.getCurrentLongitude() == null)
            throw new RuntimeConflictException("Driver current latitude and longitude are required");

        User mappedUser = new User();
        mappedUser.setName(driverSignupDto.getName());
        mappedUser.setEmail(driverSignupDto.getEmail());
        mappedUser.setPassword(passwordEncoder.encode(driverSignupDto.getPassword()));
        mappedUser.setRoles(Set.of(Role.DRIVER));
        User savedUser = userRepository.save(mappedUser);

        walletService.createNewWallet(savedUser);

        Driver createDriver = Driver.builder()
                .user(savedUser)
                .rating(0.0)
                .vehicleId(driverSignupDto.getVehicleId())
                .available(true)
                .currentLocation(GeometryUtil.createPoint(new PointDto(new double[]{
                        driverSignupDto.getCurrentLongitude(),
                        driverSignupDto.getCurrentLatitude()
                })))
                .build();

        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId , OnboardDriverDto onboardDriverDto) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with is:"+ userId));

        if(user.getRoles().contains(Role.DRIVER))
            throw new RuntimeException("User with id "+userId+"is already an driver");

        if(onboardDriverDto.getCurrentLatitude() == null || onboardDriverDto.getCurrentLongitude() == null)
            throw new RuntimeConflictException("Driver current latitude and longitude are required");

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(onboardDriverDto.getVehicleId())
                .available(true)
                .currentLocation(GeometryUtil.createPoint(new PointDto(new double[]{
                        onboardDriverDto.getCurrentLongitude(),
                        onboardDriverDto.getCurrentLatitude()
                })))
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver,DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
