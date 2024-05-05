package com.example.authserver.dao;

import com.example.authserver.ds.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,String> {
    Optional<Otp> findOtpByUserName(String username);
}
