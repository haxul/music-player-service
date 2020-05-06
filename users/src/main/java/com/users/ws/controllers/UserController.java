package com.users.ws.controllers;

import com.users.ws.dto.UserDto;
import com.users.ws.models.RegisterRequest;
import com.users.ws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String test() {
        return "HELLO WORLD";
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid RegisterRequest request) {
        return userService.createUser(request);
    }
}
