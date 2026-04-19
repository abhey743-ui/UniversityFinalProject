package com.AuthService.Security;
import com.AuthService.Dto.*;
import com.AuthService.FeignClient.UserClient;
import com.AuthService.Security.SecurityUtils.JwtToken;
import com.AuthService.Security.SecurityUtils.UserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


public class UsernamePasswordSecurityFilter extends UsernamePasswordAuthenticationFilter {
    private final  AuthenticationManager authenticationManager;
    private  final  ObjectMapper objectMapper;
    private final UserClient userClient;
    private final JwtToken jwtToken;
    public UsernamePasswordSecurityFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, UserClient userClient,JwtToken jwtToken){
              this.authenticationManager = authenticationManager;
              this.objectMapper = objectMapper;
              this.userClient = userClient;
              this.jwtToken = jwtToken;
              setAuthenticationManager(authenticationManager);
             setFilterProcessesUrl("/auth/user-login");

    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDto user;

        try {
            user = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserInfoDto userInfoDto = userClient.getUserInfo(user.getUserName());

        if (userInfoDto == null) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(),
                    Map.of(
                            "status", "NO_ACCOUNT",
                            "message", "No account found. Please create a new account."
                    ));
            return null;
        }


        if (userInfoDto.getPassword() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(),
                    Map.of(
                            "status", "GOOGLE_ACCOUNT",
                            "message", "You signed up using Google. Please login with Google to set your password."
                    ));
            return null;
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetail user  = (UserDetail) authResult.getPrincipal();

        UserInfoForToken userInfoForToken = userClient.getUserInfoForToken(user.getUsername());

        String token = jwtToken.getToken(userInfoForToken);
        Cookie cookie  = new Cookie("JWT",token);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(300*300*300);
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(),
                Map.of( "status", "WRONG_PASSWORD", "message"
                        , "Incorrect username or password. Please reset your password if you forgot it." ));
    }
}
