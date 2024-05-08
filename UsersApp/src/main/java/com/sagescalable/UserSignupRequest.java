package com.sagescalable;

public record UserSignupRequest(String username,
                                String password,
                                String email,
                                String firstName,
                                String lastName) {
}
