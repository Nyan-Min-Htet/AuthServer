package com.example.authserver.dao;

import com.example.authserver.ds.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,String>{

    Optional<User> findUserByUsername(String username);
}
