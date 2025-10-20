package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction ,Long> {
}
