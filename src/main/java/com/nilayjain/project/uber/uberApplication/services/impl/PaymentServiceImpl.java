package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentMethod;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import com.nilayjain.project.uber.uberApplication.repositories.PaymentRepository;
import com.nilayjain.project.uber.uberApplication.services.PaymentService;
import com.nilayjain.project.uber.uberApplication.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    @Transactional
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                        .orElseThrow(()->new RuntimeException("payment not found for ride: "+ride.getId()));
        if (PaymentStatus.CONFIRMED.equals(payment.getPaymentStatus())) {
            return;
        }
        if (payment.getPaymentMethod() == null) {
            payment.setPaymentMethod(ride.getPaymentMethod() == null ? PaymentMethod.CASH : ride.getPaymentMethod());
        }
        if (payment.getAmount() == null) {
            payment.setAmount(ride.getFare());
        }
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    @Transactional
    public Payment createNewPayment(Ride ride) {
        Payment payment=Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod() == null ? PaymentMethod.CASH : ride.getPaymentMethod())
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
