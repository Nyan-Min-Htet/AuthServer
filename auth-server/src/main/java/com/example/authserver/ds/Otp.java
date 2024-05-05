package com.example.authserver.ds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Otp {
    @Id
    private String userName;
    private String code;

}
