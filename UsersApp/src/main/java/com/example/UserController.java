package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("users")
public record UserController(UserService userService) {

    @PostMapping
    public void signUp(@RequestBody UserSignupRequest UserSignupRequest){
        log.info("New user Signup", UserSignupRequest);
        userService.signUpUser(UserSignupRequest);
    }
}
