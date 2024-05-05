package com.example.authserver.service;

import com.example.authserver.dao.OtpRepository;
import com.example.authserver.dao.UserRepository;
import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import com.example.authserver.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> optionalOtp = otpRepository.findOtpByUserName(otpToValidate.getUserName());
        if (optionalOtp.isPresent()) {
            Otp otp = optionalOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }
        return false;
    }

    public void auth(User user) {
        Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad Credentials!");
            }
        }else{
            throw new BadCredentialsException("Bad Credentials!");
        }
    }

    private void renewOtp(User user) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findOtpByUserName(user.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUserName(user.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }
}
