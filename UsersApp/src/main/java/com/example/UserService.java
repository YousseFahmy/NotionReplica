package com.example;

import org.springframework.stereotype.Service;

import java.beans.Customizer;
@Service
public class UserService {
    public void signUpUser(UserSignupRequest request){
        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }
}
