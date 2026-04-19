package com.AuthService.Security;
import com.AuthService.Dto.FindCredentialsForOauth2;
import com.AuthService.Dto.Oauth2InfoDto;
import com.AuthService.Dto.ProviderIdAndNameDto;
import com.AuthService.Dto.UserInfoForToken;
import com.AuthService.FeignClient.UserClient;
import com.AuthService.Security.SecurityUtils.JwtToken;
import com.AuthService.Security.SecurityUtils.ProviderId;
import com.AuthService.Security.SecurityUtils.ProviderName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Objects;



public class Oauth2LoginSuccess implements AuthenticationSuccessHandler {

               ProviderName providerName;
               ProviderId providerId;
               UserClient userClient;
               JwtToken jwtToken;


     Oauth2LoginSuccess(ProviderName providerName, ProviderId providerId, UserClient userClient, JwtToken jwtToken){

                  this.providerName = providerName;
                  this.providerId = providerId;
                  this.userClient = userClient;
                  this.jwtToken = jwtToken;

   }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

               OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
               OAuth2User oAuth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
               String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
               String gmail = oAuth2User.getAttribute("email");

               String  name = providerName.getProviderName((authorizedClientRegistrationId));
               String id = providerId.getProviderId(name,oAuth2User);

               Oauth2InfoDto oauth2InfoDto = userClient.getInfo(gmail);

               if(oauth2InfoDto != null){

                   if(oauth2InfoDto.getProviderId() == null && oauth2InfoDto.getProviderName() == null){

                       ProviderIdAndNameDto providerIdAndNameDto = new ProviderIdAndNameDto();
                       providerIdAndNameDto.setProviderId(id);
                       providerIdAndNameDto.setProviderName(name);

                       userClient.setOauth2DetailToUsername(providerIdAndNameDto,gmail);

                   }
               }else {

                           Oauth2InfoDto oauth2InfoDto1 = new Oauth2InfoDto();
                           oauth2InfoDto1.setProviderName(name);
                           oauth2InfoDto1.setUserName(gmail);
                           oauth2InfoDto1.setProviderId(id);
                           userClient.setOauth2Information(oauth2InfoDto1);
               }

                  UserInfoForToken userInfoForToken =userClient.getUserInfoForToken(gmail);
                  String token = jwtToken.getToken(userInfoForToken);
                  Cookie cookie = new Cookie("JWT",token);
                  cookie.setHttpOnly(true);
                  cookie.setPath("/");
                  cookie.setDomain("localhost");
                  cookie.setSecure(false);
                  cookie.setMaxAge(30*30*30);
                  response.addCookie(cookie);
                  return;


    }
}
