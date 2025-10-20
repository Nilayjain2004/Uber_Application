package com.nilayjain.project.uber.uberApplication.dto;

import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionMethod;
import com.nilayjain.project.uber.uberApplication.entities.enums.TransactionType;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
@Data
@Builder
public class WalletTransactionDto {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;

    private RideDto ride;
    private  String transactionId;

    private WalletDto wallet;

    private LocalDateTime timeStamp;
}
