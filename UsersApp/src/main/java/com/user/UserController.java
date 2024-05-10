package com.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("users")
public record UserController(UserSignUp UserSignUp, UserLogin userLogin) {

    @PostMapping
    public void signUp(@RequestBody UserData UserSignupRequest){
        log.info("New user Signup");
        UserSignUp.execute(UserSignupRequest);
    }
}
