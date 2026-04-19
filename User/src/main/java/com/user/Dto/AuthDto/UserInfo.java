package com.user.Dto.AuthDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {

    private String userName;

    @Column(name = "password",nullable = false)
    private String password;
}
