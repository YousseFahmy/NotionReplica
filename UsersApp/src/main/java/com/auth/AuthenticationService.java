package com.auth;

import com.user.UserData;
import org.apache.coyote.Request;

public interface AuthenticationService {
    public AuthenticationResponse execute();

}
