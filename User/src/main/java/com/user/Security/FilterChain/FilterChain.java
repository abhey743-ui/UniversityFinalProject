package com.user.Security.FilterChain;

import com.user.Repository.UserRepository.PermissionRepository;
import com.user.Repository.UserRepository.RoleRepository;
import com.user.Security.JwtVerificationFilter.JwtVerificationFilter;
import com.user.Security.SecitityUtilities.JwtToken.JwtToken;
import com.user.Repository.UserRepository.UserRepository;
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
public class FilterChain {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public JwtVerificationFilter jwtVerificationFilter(JwtToken jwtToken, AuthenticationManager authenticationManager, UserRepository userRepository
            , RoleRepository roleRepository
            , PermissionRepository permissionRepository){

            return new JwtVerificationFilter(jwtToken,authenticationManager,userRepository,roleRepository,permissionRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtVerificationFilter jwtVerificationFilter) throws Exception {

                  return httpSecurity.sessionManagement(
                          session->
                                  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                          .csrf(AbstractHttpConfigurer::disable)
                          .authorizeHttpRequests(auth->
                                  auth.requestMatchers("/internal/**").hasRole("INTERNAL_SERVICE").
                                  anyRequest().authenticated())
                          .addFilterBefore(jwtVerificationFilter,
                                  UsernamePasswordAuthenticationFilter.class)
                          .build();

    }

}
