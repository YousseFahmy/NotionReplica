package com.notionreplica.userapp.services.command.update;

import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ChangeEmail implements CommandInterface {
    UserRepository repository;
    String id;
    String newEmail;

    @Override
    public String execute() throws Exception{
        UUID ID = UUID.fromString(id);
        User user = repository.findById(ID).orElseThrow();
        user.setEmail(newEmail);
        repository.save(user);
        return "Email updated";
    }
}
