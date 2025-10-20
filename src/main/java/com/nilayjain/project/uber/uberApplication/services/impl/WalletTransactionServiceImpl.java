package com.nilayjain.project.uber.uberApplication.services.impl;

import com.nilayjain.project.uber.uberApplication.dto.WalletTransactionDto;
import com.nilayjain.project.uber.uberApplication.entities.WalletTransaction;
import com.nilayjain.project.uber.uberApplication.repositories.WalletTransactionRepository;
import com.nilayjain.project.uber.uberApplication.services.WalletTransactionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);

    }
}