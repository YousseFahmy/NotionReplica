package com.auth;

import com.user.User;
import com.user.UserRepository;

import java.util.UUID;

public record AuthenticationChangeName(String request, UserRepository repository, String newFirstName, String newLastName) implements AuthenticationService<String> {
    @Override
    public String execute() {
        UUID ID = UUID.fromString(request);
        User user = repository.findById(ID).orElseThrow();
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        repository.save(user);
        return "Email updated";
    }
}
