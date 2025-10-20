package com.nilayjain.project.uber.uberApplication.entities;

import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentMethod;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Payment {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    private  Ride ride;

    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    private LocalDateTime paymentTime;
}
