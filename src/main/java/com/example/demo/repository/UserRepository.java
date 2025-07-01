package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN u.roles WHERE u.username= ?1")
    Optional<User> findByUsernameWithRoles(String username);

}
