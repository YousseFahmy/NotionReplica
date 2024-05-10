package com.user;

import org.springframework.stereotype.Service;

@Service
public record UserSignUp(UserRepository userRepository) implements UserServiceWithData {

    @Override
    public void execute(UserData request){
        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        userRepository.save(user);
    }
}
