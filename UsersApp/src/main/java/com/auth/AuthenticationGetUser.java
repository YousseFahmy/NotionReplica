package com.auth;

import com.user.UserRepository;
import com.user.User;

public record AuthenticationGetUser(UserRepository repository, String username) implements AuthenticationService<User> {
    @Override
    public User execute() {
        return repository.findByUsername(username).orElseThrow();
    }
}
