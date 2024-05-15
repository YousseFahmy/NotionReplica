package com.auth;

import com.user.UserRepository;

public record AuthenticationDeleteUser(String request, UserRepository repository) implements AuthenticationService<String>{
    @Override
    public String execute() {
        repository.deleteByUsername(request);
        return "User deleted";
    }
}
