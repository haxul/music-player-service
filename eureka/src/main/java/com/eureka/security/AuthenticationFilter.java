package com.eureka.security;

import com.eureka.entities.UserEntity;
import com.eureka.models.LoginRequest;
import com.eureka.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

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

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("token.salt"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        String roles =user.getRoles().stream().map(e->e.getName()).collect(Collectors.joining(","));
        String token = Jwts.builder().
                setSubject(String.valueOf(user.getId())).
                claim("roles", roles).
                setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration")))).
                signWith(signatureAlgorithm, signingKey).compact();

        response.addHeader("token", token);
        response.addHeader("userId", String.valueOf(user.getId()));
    }


}
