package com.revapathah.journey_preparation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revapathah.journey_preparation.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId) ;
    

}

