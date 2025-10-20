<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import com.nilayjain.project.uber.uberApplication.repositories.PaymentRepository;
import com.nilayjain.project.uber.uberApplication.services.PaymentService;
import com.nilayjain.project.uber.uberApplication.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                        .orElseThrow(()->new RuntimeException("payment not found for ride: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment=Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
=======
package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import com.nilayjain.project.uber.uberApplication.repositories.PaymentRepository;
import com.nilayjain.project.uber.uberApplication.services.PaymentService;
import com.nilayjain.project.uber.uberApplication.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                        .orElseThrow(()->new RuntimeException("payment not found for ride: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment=Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
>>>>>>> master
