package com.users.ws.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.ws.entities.UserEntity;
import com.users.ws.models.LoginRequest;
import com.users.ws.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(UserService userService, AuthenticationManager authenticationManager, Environment environment) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        Authentication authentication = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>())
        );
        return authentication;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserEntity user = userService.findUserByUsername(username);
        System.out.println(environment.getProperty("token.expiration"));
        System.out.println(environment.getProperty("token.salt"));
        String token = Jwts.builder().
                setSubject(String.valueOf(user.getId())).
                setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration")))).
                signWith(SignatureAlgorithm.HS512, environment.getProperty("token.salt")).compact();

        response.addHeader("token", token);
        response.addHeader("userId", String.valueOf(user.getId()));
    }
}
