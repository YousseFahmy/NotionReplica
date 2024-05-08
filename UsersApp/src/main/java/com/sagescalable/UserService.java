package com.sagescalable;

import org.springframework.stereotype.Service;

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
