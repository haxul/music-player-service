package com.users.ws.services;

import com.users.ws.entities.UserEntity;
import com.users.ws.models.RegisterRequest;
import com.users.ws.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(RegisterRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setSurname(request.getSurname());
        userRepository.save(userEntity);
        return userEntity;
    }
}
