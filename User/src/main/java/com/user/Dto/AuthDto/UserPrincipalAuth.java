package com.user.Dto.AuthDto;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipalAuth {
    private Long id;
    private String userName;

}
