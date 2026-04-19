package com.user.Service.FeingService;

import com.user.Dto.AuthDto.*;
import com.user.Entity.User;
import com.user.Entity.UserCredentials;
import com.user.Repository.FeingRepository.AuthFeignRepository;
import com.user.Repository.UserRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class AuthFeign {

   private final   AuthFeignRepository authFeignRepository;
   private final PasswordEncoder passwordEncoder;
   private final UserRepository userRepository;

    public AuthFeign(AuthFeignRepository authFeignRepository, PasswordEncoder passwordEncoder,UserRepository userRepository) {
        this.authFeignRepository = authFeignRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserInfo getUserInfo(String userName){
             UserInfo userInfo =  authFeignRepository.getUserCredentialDetails(userName);

              return  userInfo;
    }

    public UserInfoForToken getUserInfoForToken(String userName){
              return authFeignRepository.getUserInfoForToken(userName);
    }

    public Oauth2Info getOauth2LoginInfo(Oauth2Info oauth2Info){
             String username = oauth2Info.getUserName();
             String providerId =  oauth2Info.getProviderId();
             String providerName = oauth2Info.getProviderName();
             return authFeignRepository.getOauth2Info(username,providerId,providerName);
    }

    @Transactional
    public void setOauth2LoginInfo(Oauth2Info oauth2Info){
        UserCredentials userCredentials = new UserCredentials();

        User user = new User();

        userCredentials.setUserName(oauth2Info.getUserName());
        userCredentials.setProviderId(oauth2Info.getProviderId());
        userCredentials.setProviderName(oauth2Info.getProviderName());
        userCredentials.setUserId(user);

        authFeignRepository.save(userCredentials);

    }

    @Transactional
    public void setProviderIdAndUserName(ProviderIdAndUsername providerIdAndUserName, String userName){

        UserCredentials userCredentials = authFeignRepository.findByUserName(userName);
        userCredentials.setProviderName(providerIdAndUserName.getProviderName());
        userCredentials.setProviderId(providerIdAndUserName.getProviderId());
    }

    public Oauth2Info getInfo(String userName){

                return  authFeignRepository.getInfo(userName);
    }

    @Transactional
    public SignupResponse signup(RegisterDto registerDto) {

        UserCredentials checkUser = authFeignRepository.findByUserName(registerDto.getUserName());

        if (checkUser != null) {
            return new SignupResponse(
                    "USER_EXISTS",
                    "User already exists. Please login.",
                    null
            );
        }

        UserCredentials user = new UserCredentials();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        UserCredentials savedUser = authFeignRepository.save(user);

        return new SignupResponse(
                "SUCCESS",
                "User created successfully.",
                savedUser.getId()
        );
    }




}
