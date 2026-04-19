package com.AuthService.Service.AuthService;

import com.AuthService.Dto.RegisterDto;
import com.AuthService.Dto.SignupResponseDto;
import com.AuthService.FeignClient.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@AllArgsConstructor
public class AuthService {

       private final UserClient userClient;

       public SignupResponseDto RegisterUser(RegisterDto registerDto){

                   return   userClient.CreateNewAccount(registerDto);
       }

}
