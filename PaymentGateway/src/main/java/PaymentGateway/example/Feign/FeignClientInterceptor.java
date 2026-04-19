package PaymentGateway.example.Feign;
import PaymentGateway.example.Security.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;


@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private final JwtUtil jwtUtil;

    public FeignClientInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = jwtUtil.generateToken();
        template.header("Authorization", "Bearer " + token);

    }
}
