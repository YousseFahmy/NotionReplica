package com.notionreplica.userapp.services.command.create;

import com.notionreplica.userapp.exceptions.UserAlreadyExistsException;
import com.notionreplica.userapp.services.command.CommandInterface;
import com.notionreplica.userapp.entities.RegisterRequest;
import com.notionreplica.userapp.entities.Role;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class SignUp implements CommandInterface {
    UserRepository repository;
    RegisterRequest request;
    PasswordEncoder passwordEncoder;


    public User execute() throws Exception{
        if(!repository.existsByEmail(request.getEmail()) && !repository.existsByUsername(request.getUsername())) {
            User user = User.builder()
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(Role.USER)
                    .build();
            repository.save(user);
            return user;
        }
        else{
            throw new UserAlreadyExistsException("");
        }
    }
}
