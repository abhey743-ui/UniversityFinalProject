package ApiGatewayService.Security.SecurityFilterChain;
//import ApiGatewayService.Security.JwtFilteringChain.JwtFilteringChain;
//import ApiGatewayService.Security.JwtFilteringChain.JwtFilteringChain;
import ApiGatewayService.Security.JwtFilteringChain.JwtFilteringChain;
import ApiGatewayService.Security.SecurityUtil.JwtTokenVerification;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

@Configuration
public class SecurityFilterChainConfig {


    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder,
                                     ReactiveOAuth2AuthorizedClientManager clientManager) {

        return builder.routes()

                .route("PRODUCTMANAGEMENT", r -> r
                        .path("/management/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter((exchange, chain) ->
                                        exchange.getPrincipal()
                                                .cast(OAuth2AuthenticationToken.class)
                                                .flatMap(auth -> clientManager.authorize(
                                                        OAuth2AuthorizeRequest
                                                                .withClientRegistrationId(auth.getAuthorizedClientRegistrationId())
                                                                .principal(auth)
                                                                .build()
                                                ))
                                                .flatMap(authorizedClient -> {

                                                    String token = authorizedClient.getAccessToken().getTokenValue();

                                                    ServerHttpRequest originalRequest = exchange.getRequest();


                                                    HttpHeaders mutableHeaders = new HttpHeaders();
                                                    mutableHeaders.putAll(originalRequest.getHeaders());
                                                    mutableHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

                                                    ServerHttpRequest decoratedRequest =
                                                            new ServerHttpRequestDecorator(originalRequest) {
                                                                @Override
                                                                public HttpHeaders getHeaders() {
                                                                    return mutableHeaders;
                                                                }
                                                            };

                                                    return chain.filter(exchange.mutate()
                                                            .request(decoratedRequest)
                                                            .build());
                                                })
                                                .switchIfEmpty(chain.filter(exchange))
                                )
                        )
                        .uri("lb://PRODUCTMANAGEMENT")
                )

                .build();
    }

    @Bean
    JwtFilteringChain jwtFilteringChain(JwtTokenVerification jwtTokenVerification){
                        return  new JwtFilteringChain(jwtTokenVerification);
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity, JwtFilteringChain jwtFilteringChain) {
               return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                       .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                       .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                       .cors(cors -> cors.configurationSource(exchange -> {
                           CorsConfiguration config = new CorsConfiguration();
                           config.setAllowCredentials(true);
                           config.addAllowedOriginPattern("*");
                           config.addAllowedHeader("*");
                           config.addAllowedMethod("*");
                           return config;
                       }))
                    .securityContextRepository(new WebSessionServerSecurityContextRepository())
                       .authorizeExchange(authorizeExchange->
                         authorizeExchange.pathMatchers("/auth/**","/login/oauth2/**","/token/**","/products/**","/reviews/public/**","/login/**","/validate/token")
                                 .permitAll()
                                 .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyExchange().authenticated()


                ).oauth2Login(Customizer.withDefaults())

                        .oauth2Client(Customizer.withDefaults())
                       .addFilterAfter(jwtFilteringChain, SecurityWebFiltersOrder.AUTHENTICATION)

                       .build();


    }



}
