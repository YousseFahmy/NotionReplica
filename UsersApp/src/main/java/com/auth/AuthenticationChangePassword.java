package com.auth;

import com.user.User;
import com.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public record AuthenticationChangePassword(String request, UserRepository repository, PasswordEncoder passwordEncoder, String newPassword, String oldPassword) implements AuthenticationService<String> {

    @Override
    public String execute() {
        UUID ID = UUID.fromString(request);
        User user = repository.findById(ID).orElseThrow();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            repository.save(user);
        }
        else {
            return "Incorrect old password";
        }
        return "Password updated";
    }
}
