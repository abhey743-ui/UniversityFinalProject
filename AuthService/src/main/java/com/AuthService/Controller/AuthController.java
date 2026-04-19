package com.AuthService.Controller;
import com.AuthService.Dto.RegisterDto;
import com.AuthService.Dto.SignupResponseDto;
import com.AuthService.Service.AuthService.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTML;


@RestController
@AllArgsConstructor
public class AuthController {

         private final AuthService authService;

         @PostMapping("auth/register/user")
         public ResponseEntity<SignupResponseDto> RegisterUser(@RequestBody RegisterDto registerDto){


             SignupResponseDto signupResponseDto = authService.RegisterUser(registerDto);
             if(signupResponseDto.getStatus().equals("SUCCESS")){
                 return ResponseEntity.status(HttpStatus.CREATED).body(signupResponseDto);
             }

             if(signupResponseDto.getStatus().equals("USER_EXISTS")){
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(signupResponseDto);
             }

             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(signupResponseDto);

         }
}
