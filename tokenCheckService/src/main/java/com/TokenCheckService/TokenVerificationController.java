package com.TokenCheckService;

import lombok.AllArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenVerificationController {



    private final TokenVerificationService tokenVerificationService;

    @GetMapping("/validate/token")
    public Boolean verifyToken(@CookieValue(name = "JWT",required = false) String token){
             return  tokenVerificationService.verifyToken(token);



    }
}
