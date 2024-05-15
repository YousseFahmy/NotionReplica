package com.auth;

import com.user.UserRepository;

public record AuthenticationDeleteUser(AuthenticationRequest request, UserRepository repository) implements AuthenticationService<String>{
    @Override
    public String execute() {
        repository.deleteByUsername(request.getUsername());
        return "User deleted";
    }
}
