package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;

public interface PaymentService {
void processPayment(Ride ride);
Payment createNewPayment(Ride ride);
void updatePaymentStatus(Payment payment, PaymentStatus status);
}
