package com.auth;

import com.config.JwtService;
import com.user.User;
import com.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record AuthenticationLogin(AuthenticationManager authenticationManager, UserRepository repository,
                                  JwtService jwtService, AuthenticationRequest request) implements AuthenticationService<AuthenticationResponse> {
    public AuthenticationResponse execute() {
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
