package ApiGatewayService.Security.JwtFilteringChain;
import ApiGatewayService.Security.SecurityUtil.JwtTokenVerification;
import io.jsonwebtoken.Claims;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.Collections;



@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class JwtFilteringChain implements WebFilter {

    private final JwtTokenVerification jwtTokenVerification;

    public JwtFilteringChain(JwtTokenVerification jwtTokenVerification) {
        this.jwtTokenVerification = jwtTokenVerification;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return exchange.getSession().flatMap(session -> {

            Object ctx = session.getAttributes().get("SPRING_SECURITY_CONTEXT");
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith("/auth") ||
                    path.startsWith("/login") ||
                    path.startsWith("/token") ||
                    path.startsWith("/products") ||
                    path.startsWith("/reviews/public") ||
                    path.startsWith("/management") ||
                    path.startsWith("/testing") || path.startsWith("/validate/token")) {

                return chain.filter(exchange);
            }

            if (ctx instanceof org.springframework.security.core.context.SecurityContext sc &&
                    sc.getAuthentication() != null &&
                    sc.getAuthentication().isAuthenticated()) {

                return chain.filter(exchange);
            }

            String token = null;
            if (exchange.getRequest().getCookies().getFirst("JWT") != null) {
                token = exchange.getRequest().getCookies().getFirst("JWT").getValue();
            }

            if (token == null) {
                return chain.filter(exchange);
            }

            Claims claims;
            try {
                claims = jwtTokenVerification.validate(token);
            } catch (Exception e) {
                return chain.filter(exchange);
            }

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Collections.emptyList()
                    );

            String finalToken = token;
            ServerHttpRequest decoratedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    HttpHeaders headers = new HttpHeaders();
                    headers.putAll(super.getHeaders());
                    headers.set("Authorization", finalToken);
                    return headers;
                }
            };

            ServerWebExchange newExchange = exchange.mutate()
                    .request(decoratedRequest)
                    .build();

            return chain.filter(newExchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        });
    }
}




