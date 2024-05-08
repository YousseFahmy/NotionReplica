package com.example;

public record UserSignupRequest(String id,
                                String username,
                                String password,
                                String email,
                                String firstName,
                                String lastName) {
}
