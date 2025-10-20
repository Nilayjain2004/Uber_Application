package com.nilayjain.project.uber.uberApplication.strategies.impl;

import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionMethod;
import com.nilayjain.project.uber.uberApplication.repositories.PaymentRepository;
import com.nilayjain.project.uber.uberApplication.services.PaymentService;
import com.nilayjain.project.uber.uberApplication.services.WalletService;
import com.nilayjain.project.uber.uberApplication.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Driver -> 70 deduct 30 rs from Drivers wallet

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private  final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
