package com.AuthService.Security;
import com.AuthService.FeignClient.UserClient;
import com.AuthService.Security.SecurityUtils.JwtToken;
import com.AuthService.Security.SecurityUtils.ProviderId;
import com.AuthService.Security.SecurityUtils.ProviderName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                 return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public ObjectMapper objectMapper(){
         return new ObjectMapper();
    }

    @Bean
    UsernamePasswordSecurityFilter usernamePasswordSecurityFilter(AuthenticationManager authenticationManager,ObjectMapper objectMapper,UserClient userClient,JwtToken jwtToken){
                     return new UsernamePasswordSecurityFilter(authenticationManager,objectMapper,userClient,jwtToken);
    }

    @Bean
    public Oauth2LoginSuccess oauth2LoginSuccess(ProviderId providerId, ProviderName providerName, UserClient userClient, JwtToken jwtToken){
          return new Oauth2LoginSuccess(providerName,providerId,userClient,jwtToken);

    }
    @Bean public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder(); }


    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http,
                                              Oauth2LoginSuccess oauth2LoginSuccess,
                                              UsernamePasswordSecurityFilter usernamePasswordSecurityFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/user-login", "auth/register/user", "/auth/forgot-password", "/auth/reset-password").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .successHandler(oauth2LoginSuccess)
                )

                .addFilterBefore(usernamePasswordSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
