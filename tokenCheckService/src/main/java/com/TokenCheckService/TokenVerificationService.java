package com.TokenCheckService;

import com.TokenCheckService.TokenUtilities.TokenVerification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenVerificationService {

    private final TokenVerification tokenVerification;
    public Boolean verifyToken(String token){

        if (token == null || token.isBlank()) {

            return false; }

        try {
              tokenVerification.verifyToken(token);

             } catch (RuntimeException e) {
                 e.printStackTrace();
                return false;
           }
                  return true;
        }
}

