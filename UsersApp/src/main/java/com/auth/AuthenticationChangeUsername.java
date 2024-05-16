package com.auth;

import com.user.User;
import com.user.UserRepository;

import java.util.UUID;

public record AuthenticationChangeUsername(String request, UserRepository repository, String newUsername) implements AuthenticationService<String> {
    @Override
    public String execute() {
        UUID ID = UUID.fromString(request);
        User user = repository.findById(ID).orElseThrow();
        user.setUsername(newUsername);
        repository.save(user);
        return "Email updated";
    }
}
