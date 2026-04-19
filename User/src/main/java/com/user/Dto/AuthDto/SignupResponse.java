package com.user.Dto.AuthDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private String status;
    private String message;
    private Long userId;

    public SignupResponse(String status, String message, Long userId) {
        this.status = status;
        this.message = message;
        this.userId = userId;
    }


}
