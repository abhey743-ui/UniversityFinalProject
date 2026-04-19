package PaymentGateway.example.Security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Getter
@Component
@AllArgsConstructor
public class JwtUtil {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("hhfdsjkfhskjfhewkhfdskllncsklfjwkl93572095532%#$53".getBytes());

    public String generateToken() {
        return Jwts.builder()
                .setSubject("ORDER-SERVICE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

}
