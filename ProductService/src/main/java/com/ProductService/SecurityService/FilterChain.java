package com.ProductService.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class FilterChain {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                           return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtFiltering jwtFiltering(TokenVerification tokenVerification){
                     return new JwtFiltering(tokenVerification);
    }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,JwtFiltering jwtFiltering) throws Exception {
        return httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth-> auth.requestMatchers("/products/**").permitAll().requestMatchers("/reviews/public/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFiltering, UsernamePasswordAuthenticationFilter.class).build();

   }

}
