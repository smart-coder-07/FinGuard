package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.User;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
