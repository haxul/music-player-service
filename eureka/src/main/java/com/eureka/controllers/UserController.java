package com.eureka.controllers;

import com.eureka.entities.UserEntity;
import com.eureka.models.RegisterRequest;
import com.eureka.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserEntity createUser(@RequestBody RegisterRequest request) throws Exception {
        return userService.createUser(request);
    }

    @GetMapping
    public String test(Authentication authentication) {
        return  "Hello, id=" + String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    }
}
