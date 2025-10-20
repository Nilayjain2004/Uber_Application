package com.nilayjain.project.uber.uberApplication.dto;
import lombok.Data;

import java.util.List;
@Data
public class WalletDto {
    private Long id;

    private UserDto user;
    private Double balance;

    private List<WalletTransactionDto> transactions;

}
