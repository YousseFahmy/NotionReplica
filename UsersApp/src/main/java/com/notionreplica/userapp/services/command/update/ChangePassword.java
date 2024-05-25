package com.notionreplica.userapp.services.command.update;

import com.notionreplica.userapp.exceptions.IncorrectCredentialsException;
import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@AllArgsConstructor
public class ChangePassword implements CommandInterface {
    UserRepository repository;
    PasswordEncoder passwordEncoder;
    String id;
    String newPassword;
    String oldPassword;

    @Override
    public String execute() throws Exception{
        UUID ID = UUID.fromString(id);
        User user = repository.findById(ID).orElseThrow();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            repository.save(user);
        }
        else {
            throw new IncorrectCredentialsException("Incorrect credentials");
        }
        return "Password updated";
    }
}
