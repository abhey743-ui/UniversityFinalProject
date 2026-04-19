package com.user.Dto.AuthDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RegisterDto {
    private String userName;
    private String password;
}
