package com.eureka.services;

import com.eureka.entities.RoleEntity;
import com.eureka.entities.UserEntity;
import com.eureka.exceptions.UserExistsException;
import com.eureka.models.RegisterRequest;
import com.eureka.repositories.RoleRepository;
import com.eureka.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserEntity createUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) throw new UserExistsException();
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword( bCryptPasswordEncoder.encode(request.getPassword()));
        userEntity.setUsername(request.getUsername());
        RoleEntity preAuthRole = roleRepository.findByName("USER");

        if (preAuthRole == null) {
            preAuthRole = new RoleEntity("USER");
            roleRepository.save(preAuthRole);
        }

        userEntity.getRoles().add(preAuthRole);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(s);
        if (userEntity == null) throw new UsernameNotFoundException(s);

        return new User(userEntity.getUsername(), userEntity.getPassword(),
                userEntity.getIsEnable(),true,true,true,
                new ArrayList<>());
    }

    public UserEntity findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return userEntity;
    }

    public UserEntity findById(int id) {
        return userRepository.findById(id).orElseGet(null);
    }

}
