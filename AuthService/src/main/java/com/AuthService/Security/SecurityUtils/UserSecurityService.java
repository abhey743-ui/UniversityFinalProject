package com.AuthService.Security.SecurityUtils;

import com.AuthService.Dto.UserInfoDto;
import com.AuthService.FeignClient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfoDto userInfo = userClient.getUserInfo(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserDetail(
                userInfo.getUserName(),
                userInfo.getPassword()
        );
    }
}
