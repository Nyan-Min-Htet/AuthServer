package com.example.authserver.controller;

import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import com.example.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.created(URI.create("/user/add"))
                .body(user);
    }
    @PostMapping("/user/auth")
    public void auth(@RequestBody User user){
        userService.auth(user);
    }
    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response){
        if (userService.check(otp)){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
