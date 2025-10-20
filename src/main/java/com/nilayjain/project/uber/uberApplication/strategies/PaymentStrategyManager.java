package com.nilayjain.project.uber.uberApplication.strategies;

import com.nilayjain.project.uber.uberApplication.entities.enums.PaymentMethod;
import com.nilayjain.project.uber.uberApplication.services.WalletTransactionService;
import com.nilayjain.project.uber.uberApplication.strategies.impl.CashPaymentStrategy;
import com.nilayjain.project.uber.uberApplication.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
private final WalletPaymentStrategy walletPaymentStrategy;
private final CashPaymentStrategy cashPaymentStrategy;

public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
    return switch (paymentMethod){
        case CASH -> cashPaymentStrategy;
        case WALLET -> walletPaymentStrategy;
    };
}
}
