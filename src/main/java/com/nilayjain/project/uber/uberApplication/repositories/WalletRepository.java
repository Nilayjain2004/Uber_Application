package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.User;
import com.nilayjain.project.uber.uberApplication.entities.Wallet;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByUser(User user);
}


