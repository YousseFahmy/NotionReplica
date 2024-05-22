package com.notionreplica.userapp.services.command.delete;

import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
public class DeleteUser implements CommandInterface {
    UserRepository repository;
    String id;
    @Override
    public String execute() throws Exception{
        UUID userID = UUID.fromString(id);
        repository.deleteById(userID);
        return "User deleted";
    }
}
