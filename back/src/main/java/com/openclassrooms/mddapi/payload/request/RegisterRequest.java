package com.openclassrooms.mddapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String email;
    private String username;
    private String password;

    // getters / setters
}
