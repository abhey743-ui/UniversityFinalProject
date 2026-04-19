package com.user.Security.SecitityUtilities.JwtToken;
import com.user.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtToken {

    private final String userSecretKey =
            "7493ndddsjmkjalsdjlajdsaJDIOWUROIWDJWKXSNMLKU";

    private final String machineSecretKey =
            "hfgtsdart6sgsjdhgyutadndmdmdlsksi8esjsnxhdgwteryndjkjsl";

    public SecretKey getUserSecretKey() {
        return Keys.hmacShaKeyFor(userSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey getMachineSecretKey() {
        return Keys.hmacShaKeyFor(machineSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims verifyMachineToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getMachineSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Claims verifyUserToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getUserSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}


