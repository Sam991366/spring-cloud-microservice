package com.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);

}
