package com.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record UserLogin(UserRepository userRepository) implements UserServiceWithData{
    @Override
    public void execute(UserData userData) {
        User user = userRepository.findByUsername(userData.username()).orElseThrow();
        if(user.getPassword().equals(userData.password())){
            log.info("New user Login");
        }
    }
}
