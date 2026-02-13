package com.openclassrooms.mddapi.dto;
import com.openclassrooms.mddapi.dto.UserDto;

public class AuthResponseDto {

    private String token;
    private UserDto user;

    public AuthResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}
