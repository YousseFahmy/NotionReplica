package com.notionreplica.userapp.services.command.update;

import com.notionreplica.userapp.exceptions.UserAlreadyExistsException;
import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ChangeUsername implements CommandInterface {
    UserRepository repository;
    String id;
    String newUsername;

    @Override
    public String execute() throws Exception{
        UUID ID = UUID.fromString(id);
        User user = repository.findById(ID).orElseThrow();
        if(!repository.existsByUsername(newUsername)){
            user.setUsername(newUsername);
            repository.save(user);
            return "Username updated";
        }
        else {
            throw new UserAlreadyExistsException("User already exists");
        }

    }
}
