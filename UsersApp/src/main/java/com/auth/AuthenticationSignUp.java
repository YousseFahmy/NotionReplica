package com.auth;

import com.config.JwtService;
import com.user.Role;
import com.user.User;
import com.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AuthenticationSignUp(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, RegisterRequest request) implements AuthenticationService {

    public AuthenticationResponse execute() {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
