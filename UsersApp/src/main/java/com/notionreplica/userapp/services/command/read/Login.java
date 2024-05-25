package com.notionreplica.userapp.services.command.read;

import com.notionreplica.userapp.entities.AuthenticationRequest;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import com.notionreplica.userapp.services.command.CommandInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@AllArgsConstructor
public class Login implements CommandInterface {
    UserRepository repository;
    AuthenticationRequest request;
    AuthenticationManager authenticationManager;

    @Override
    public User execute() throws Exception{
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return user;
    }
}
