package com.user;

import org.springframework.stereotype.Service;

@Service
public interface UserServiceWithData {
    public void execute(UserData userData);
}
