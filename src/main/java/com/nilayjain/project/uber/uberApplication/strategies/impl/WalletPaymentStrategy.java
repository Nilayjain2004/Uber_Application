package com.nilayjain.project.uber.uberApplication.strategies.impl;

import com.nilayjain.project.uber.uberApplication.entities.Driver;
import com.nilayjain.project.uber.uberApplication.entities.Payment;
import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentStatus;
import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionMethod;
import com.nilayjain.project.uber.uberApplication.repositories.PaymentRepository;
import com.nilayjain.project.uber.uberApplication.services.PaymentService;
import com.nilayjain.project.uber.uberApplication.services.WalletService;
import com.nilayjain.project.uber.uberApplication.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider had 232,Driver had 500
//Ride cost is 100, commission = 30
//Rider -> 232-100 = 132
//Driver -> 500 +(100 - 30) =570
@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

         private final WalletService walletService;
         private final PaymentRepository paymentRepository;


    @Transactional
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                      payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);

        double driverCut = payment.getAmount() *(1-PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driverCut,null,payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
