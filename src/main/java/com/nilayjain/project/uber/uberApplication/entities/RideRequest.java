package com.nilayjain.project.uber.uberApplication.entities;

import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentMethod;
import com.nilayjain.project.uber.uberApplication.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_ride_request_rider", columnList = "rider_id")
})
public class RideRequest {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    @Column(columnDefinition = "Geometry(point, 4326)")// 4326 is for Earth
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(point, 4326)")// 4326 is for Earth
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch =FetchType.LAZY) //until we will not use rider till it will not call or create a database query
    private Rider rider;

    @Enumerated(EnumType.STRING)//use to tell hibernate that we are storing roles as it is in String format
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)//use to tell hibernate that we are storing roles as it is in String format
    private RideRequestStatus rideRequestStatus;

    private Double fare;

}
