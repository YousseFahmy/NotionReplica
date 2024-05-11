package com.auth;

import com.config.JwtService;
import com.user.User;
import com.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record AuthenticationLogin(AuthenticationManager authenticationManager, UserRepository repository,
                                  JwtService jwtService, AuthenticationRequest request) implements AuthenticationService {
    public AuthenticationResponse execute() {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
