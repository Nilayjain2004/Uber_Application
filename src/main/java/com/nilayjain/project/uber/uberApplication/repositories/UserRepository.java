package com.nilayjain.project.uber.uberApplication.repositories;

import com.nilayjain.project.uber.uberApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User> findByEmail(String email); // no need for the query it will be handel by jpa
    // jpa provides CURD operation without sql without writing it
    // User is a Entity
    // Long is the type of the primary key of the User Entity @ID

}
