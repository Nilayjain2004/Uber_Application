package com.nilayjain.project.uber.uberApplication.controllers;

import com.nilayjain.project.uber.uberApplication.dto.RazorpayDemoPaymentDto;
import com.nilayjain.project.uber.uberApplication.dto.WalletDto;
import com.nilayjain.project.uber.uberApplication.dto.WalletTransactionDto;
import com.nilayjain.project.uber.uberApplication.entities.User;
import com.nilayjain.project.uber.uberApplication.entities.Wallet;
import com.nilayjain.project.uber.uberApplication.entities.WalletTransaction;
import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionMethod;
import com.nilayjain.project.uber.uberApplication.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/my")
    @Transactional(readOnly = true)
    public ResponseEntity<WalletDto> getMyWallet() {
        return ResponseEntity.ok(toDto(walletService.findByUser(getCurrentUser())));
    }

    @PostMapping("/topup/demo")
    @Transactional
    public ResponseEntity<WalletDto> topUpDemo(@RequestBody RazorpayDemoPaymentDto paymentDto) {
        String paymentId = paymentDto.getRazorpayPaymentId();
        if (paymentId == null || paymentId.isBlank()) {
            paymentId = "rzp_demo_" + System.currentTimeMillis();
        }

        Wallet wallet = walletService.addMoneyToWallet(
                getCurrentUser(),
                paymentDto.getAmount(),
                paymentId,
                null,
                TransactionMethod.BANKING
        );
        return ResponseEntity.ok(toDto(wallet));
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private WalletDto toDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setBalance(wallet.getBalance());
        walletDto.setTransactions(toTransactionDtos(wallet.getTransactions()));
        return walletDto;
    }

    private List<WalletTransactionDto> toTransactionDtos(List<WalletTransaction> transactions) {
        if (transactions == null) {
            return List.of();
        }

        return transactions.stream()
                .sorted(Comparator.comparing(WalletTransaction::getTimeStamp,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(transaction -> WalletTransactionDto.builder()
                        .id(transaction.getId())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType())
                        .transactionMethod(transaction.getTransactionMethod())
                        .transactionId(transaction.getTransactionId())
                        .timeStamp(transaction.getTimeStamp())
                        .build())
                .toList();
    }
}
