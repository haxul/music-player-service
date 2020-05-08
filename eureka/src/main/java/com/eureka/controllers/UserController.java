package com.eureka.controllers;

import com.eureka.entities.UserEntity;
import com.eureka.models.RegisterRequest;
import com.eureka.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users-ws")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public UserEntity createUser(@RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserEntity> findUser(Authentication authentication) {
       UserEntity user = userService.findById((Integer) authentication.getPrincipal());
       if (user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
       return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
