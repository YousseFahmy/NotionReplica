package com.auth;

import com.user.User;
import com.user.UserRepository;

import java.util.UUID;

public record AuthenticationChangeEmail(String request, UserRepository repository, String newEmail) implements AuthenticationService<String>{
    @Override
    public String execute() {
        UUID ID = UUID.fromString(request);
        User user = repository.findById(ID).orElseThrow();
        user.setEmail(newEmail);
        repository.save(user);
        return "Email updated";
    }
}
