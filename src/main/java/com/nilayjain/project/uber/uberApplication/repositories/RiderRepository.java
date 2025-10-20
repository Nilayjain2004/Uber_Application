package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.Rider;
import com.nilayjain.project.uber.uberApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
    // jpa provides CURD operation without sql without writing it
    // Rider is a Entity
    // Long is the type of the primary key of the Rider Entity @ID
}
