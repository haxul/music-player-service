package com.eureka.security;

import com.eureka.entities.RoleEntity;
import com.eureka.repositories.UserRepository;
import com.eureka.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null) return null;
            String token = authorizationHeader.replace("Bearer ", "");
            Claims claims = Jwts.parser().
                    setSigningKey(DatatypeConverter.parseBase64Binary(environment.getProperty("token.salt"))).
                    parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            if (userId == null) return null;
            String[] roles = claims.get("roles", String.class).split(",");
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (var role : roles) {
                String roleWithPrefix = "ROLE_" + role;
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleWithPrefix);
                authorities.add(simpleGrantedAuthority);
            }
            return new UsernamePasswordAuthenticationToken(Integer.valueOf(userId), null,authorities);
        } catch (Exception e) {
            return null;
        }
    }

}
