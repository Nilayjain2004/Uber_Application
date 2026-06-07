package com.nilayjain.project.uber.uberApplication.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicle_id")
})
public class Driver {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    @OneToOne// hibernate define a unique constraint using this example rider 1 will one connect to driver 3
    @JoinColumn(name = "user_id")//the rider will have a field user_id and the user_id is the foreign key for the user
    private User user; //rider is connected to user only

    private Double rating;

    private Boolean available;

    private String vehicleId;

    @Column(columnDefinition = "Geometry(point, 4326)")// 4326 is for Earth
    private Point currentLocation;
}
