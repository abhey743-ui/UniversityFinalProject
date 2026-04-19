package com.user.Dto.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoForToken {

    private String userName;
    private Long  id;
}
