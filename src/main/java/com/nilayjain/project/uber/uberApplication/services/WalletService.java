package com.nilayjain.project.uber.uberApplication.services;

import com.nilayjain.project.uber.uberApplication.entities.Ride;
import com.nilayjain.project.uber.uberApplication.entities.User;
import com.nilayjain.project.uber.uberApplication.entities.Wallet;
import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount , String transactionId, Ride ride, TransactionMethod transactionMethod);
    Wallet deductMoneyFromWallet(User user, Double amount , String transactionId, Ride ride, TransactionMethod transactionMethod);
    void withdrawAllMyMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);
}
