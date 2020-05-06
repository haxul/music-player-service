package com.users.ws.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotNull
    @Size(min = 6)
    private String password;
    @NotNull
    @Pattern(regexp = "\\d{6,20}")
    private String username;

    private String name;

    private String surname;

}
