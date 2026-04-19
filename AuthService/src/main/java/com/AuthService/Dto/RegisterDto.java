package com.AuthService.Dto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {

    private String userName;
    private String password;
}
