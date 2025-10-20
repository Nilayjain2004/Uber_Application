package com.nilayjain.project.uber.uberApplication.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider {

    @Id // primary key without this the class will give error
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    @OneToOne// hibernate define a unique constraint using this example rider 1 will one connect to driver 3
    @JoinColumn(name = "user_id")//the rider will have a field user_id and the user_id is the foreign key for the user
    private User user; //rider is connected to user only

    private Double rating;

}
