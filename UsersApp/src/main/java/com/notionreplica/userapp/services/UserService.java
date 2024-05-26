package com.notionreplica.userapp.services;

import com.notionreplica.userapp.entities.AuthenticationRequest;
import com.notionreplica.userapp.entities.RegisterRequest;
import com.notionreplica.userapp.entities.User;
import com.notionreplica.userapp.repositories.UserRepository;
import com.notionreplica.userapp.services.command.CommandFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import static com.notionreplica.userapp.services.command.CommandInterface.*;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CommandFactory CommandFactory;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String REPLY_TOPIC = "userReplyTopic";
    private static final String REQUEST_TOPIC = "userRequestTopic";

    @KafkaListener(topics = REQUEST_TOPIC, groupId = "userServiceGroup")
    public void listenForRequests(ConsumerRecord<String, String> record) throws Exception {
        String message = record.value();
        org.json.JSONObject jsonObject = new org.json.JSONObject(message);
        String correlationId = jsonObject.getString("correlationId");
        String username = jsonObject.getString("username");

        boolean userExists = false;
        try {
            getUser(username);
            userExists = true;
        }
        catch (Exception e){
            userExists = false;
        }

        String response = String.format("{\"correlationId\":\"%s\", \"username\":\"%s\", \"userExists\":\"%s\" }",
                correlationId, username, userExists);

        // Send the response message to the reply topic
        kafkaTemplate.send(REPLY_TOPIC, response);
    }


    public User SignUp(RegisterRequest request) throws Exception {
        return (User) CommandFactory.create(SIGNUP, request, passwordEncoder).execute();
    }

    public User Login(AuthenticationRequest request) throws Exception{
        return (User) CommandFactory.create(LOGIN, request, authenticationManager).execute();
    }

    public User getUser(String username) throws Exception{
        return (User) CommandFactory.create(GET_USER, username).execute();
    }

    public String deleteUser(String id) throws Exception{
        return (String) CommandFactory.create(DELETE_USER, id).execute();
    }

    public String changePassword(String id, String newPassword, String oldPassword) throws Exception {
        return (String) CommandFactory.create(CHANGE_PASSWORD,passwordEncoder, id, newPassword, oldPassword).execute();
    }

    public String changeEmail(String id, String newEmail) throws Exception {
        return (String) CommandFactory.create(CHANGE_EMAIL, id, newEmail).execute();
    }

    public String changeUsername(String id, String newUsername) throws Exception {
        return (String) CommandFactory.create(CHANGE_USERNAME, id, newUsername).execute();
    }

    public String changeName(String id, String firstName, String lastName) throws Exception {
        return (String) CommandFactory.create(CHANGE_NAME, id, firstName, lastName).execute();
    }
}
