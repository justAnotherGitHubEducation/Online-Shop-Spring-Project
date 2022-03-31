package com.example.spring_mvc_data_crud.repos;

import com.example.spring_mvc_data_crud.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional <User> findByUsername(String username);
    Optional <User> findById(Long id);
}