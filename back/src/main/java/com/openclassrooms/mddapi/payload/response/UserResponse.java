package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    private String username;
    private String email;

    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }
}