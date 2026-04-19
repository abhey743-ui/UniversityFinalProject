package PaymentGateway.example.Security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.awt.*;
import java.nio.charset.StandardCharsets;


@Component
public class JwtValidation {
    private String Secret_Key =  "7493ndddsjmkjalsdjlajdsaJDIOWUROIWDJWKXSNMLKU";

    public SecretKey getSecret(){
        return  Keys.hmacShaKeyFor(Secret_Key.getBytes(StandardCharsets.UTF_8));
    }
    public Claims verifyToken(String token){
        return  Jwts.parserBuilder().setSigningKey(getSecret()).build().parseClaimsJws(token).getBody();
    }
}
