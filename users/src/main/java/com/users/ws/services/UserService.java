package com.users.ws.services;

import com.users.ws.dto.UserDto;
import com.users.ws.entities.UserEntity;
import com.users.ws.exceptions.UserExistsException;
import com.users.ws.models.RegisterRequest;
import com.users.ws.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public UserDto createUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) throw new UserExistsException();
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userEntity.setSurname(request.getSurname());
        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(s);
        if (user == null) throw new UsernameNotFoundException("User is not found");
        return new User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    public UserEntity findUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User is not found");
        return user;
    }

}
