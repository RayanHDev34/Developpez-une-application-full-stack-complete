package com.openclassrooms.mddapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String username;
    private String email;
    private String password;

    // getters / setters
}
