package com.notionreplica.userapp.services.command.read;

import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUser implements CommandInterface {
    UserRepository repository;
    String username;

    @Override
    public User execute() throws Exception {
        return repository.findByUsername(username).orElseThrow();
    }
}
