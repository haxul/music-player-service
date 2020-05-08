package com.eureka.services;

import com.eureka.entities.UserEntity;
import com.eureka.models.RegisterRequest;
import com.eureka.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity createUser(RegisterRequest request) throws Exception {
        if (userRepository.findByUsername(request.getUsername()) != null) throw new Exception("User exists");
        UserEntity userEntity = new UserEntity();
        userEntity.setIsBlocked(false);
        userEntity.setPassword( bCryptPasswordEncoder.encode(request.getPassword()));
        userEntity.setUsername(request.getUsername());
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(s);
        if (userEntity == null) throw new UsernameNotFoundException(s);
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

    public UserEntity findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw  new UsernameNotFoundException(username);
        return userEntity;
    }
}
