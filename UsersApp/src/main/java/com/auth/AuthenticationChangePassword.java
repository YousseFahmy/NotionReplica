package com.auth;

import com.user.User;
import com.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Slf4j
public record AuthenticationChangePassword(String request, UserRepository repository, PasswordEncoder passwordEncoder, String newPassword, String oldPassword) implements AuthenticationService<String> {

    @Override
    public String execute() {
        UUID ID = UUID.fromString(request);
        User user = repository.findById(ID).orElseThrow();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(newPassword);
            repository.save(user);
        }
        else {
            return "Incorrect old password";
        }
        return "Password updated";
    }
}
