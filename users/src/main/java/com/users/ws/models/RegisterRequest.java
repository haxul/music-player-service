package com.users.ws.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;

    private String name;
    private String surname;

}
