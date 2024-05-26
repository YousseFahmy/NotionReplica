package com.notionreplica.userapp.services.command;


import com.notionreplica.userapp.controller.AuthenticationController;
import com.notionreplica.userapp.entities.AuthenticationRequest;
import com.notionreplica.userapp.entities.RegisterRequest;
import com.notionreplica.userapp.repositories.UserRepository;
import com.notionreplica.userapp.services.command.create.SignUp;
import com.notionreplica.userapp.services.command.delete.DeleteUser;
import com.notionreplica.userapp.services.command.read.GetUser;
import com.notionreplica.userapp.services.command.read.Login;
import com.notionreplica.userapp.services.command.update.ChangeEmail;
import com.notionreplica.userapp.services.command.update.ChangeName;
import com.notionreplica.userapp.services.command.update.ChangePassword;
import com.notionreplica.userapp.services.command.update.ChangeUsername;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.notionreplica.userapp.services.command.CommandInterface.*;


@Component
public class CommandFactory {
    @Autowired
    private UserRepository repository;
    Logger log = LoggerFactory.getLogger(CommandFactory.class);

    public CommandInterface create(int commandCode, Object... params){
        switch (commandCode){
            case SIGNUP:
                return new SignUp(repository, (RegisterRequest) params[0], (PasswordEncoder) params[1]);
            case DELETE_USER:
                return new DeleteUser(repository, (String) params[0]);
            case GET_USER:
                return new GetUser(repository, (String) params[0]);
            case LOGIN:
                return new Login(repository, (AuthenticationRequest) params[0], (AuthenticationManager) params[1]);
            case CHANGE_EMAIL:
                return new ChangeEmail(repository, (String) params[0], (String) params[1]);
            case CHANGE_NAME:
                return new ChangeName(repository, (String) params[0], (String) params[1], (String) params[2]);
            case CHANGE_PASSWORD:
                return new ChangePassword(repository, (PasswordEncoder) params[0], (String) params[1], (String) params[2], (String) params[3]);
            case CHANGE_USERNAME:
                log.info((String) params[0]);
                log.info((String) params[1]);
                return new ChangeUsername(repository, (String) params[0], (String) params[1]);
        }
        return null;
    }




}
