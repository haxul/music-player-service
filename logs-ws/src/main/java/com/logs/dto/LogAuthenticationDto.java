package com.logs.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogAuthenticationDto {

    private String username;
    private Date date;
    private Boolean isSuccessfulAuthentication;
}

