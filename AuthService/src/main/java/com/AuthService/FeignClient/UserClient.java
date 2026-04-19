package com.AuthService.FeignClient;

import com.AuthService.Dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(name = "USER")
public interface UserClient {

    @GetMapping("/internal/users/get/userInfo/{userName}")
    UserInfoDto getUserInfo(@PathVariable  String userName);


    @GetMapping("/internal/users/get/info/{userName}")
    Oauth2InfoDto getInfo(@PathVariable("userName") String userName);


    @PostMapping("/internal/users/get/OauthInfo")
    Oauth2InfoDto getOauth2Info(@RequestBody Oauth2InfoDto oauth2InfoDto);


    @PostMapping("/internal/users/set/auth2/info")
    void  setOauth2Information(@RequestBody Oauth2InfoDto oauth2InfoDto);

    @GetMapping("/internal/users/get/userInfo/for/Token/{userName}")
    UserInfoForToken getUserInfoForToken(@PathVariable("userName") String userName);

    @PostMapping("/internal/users/set/provider/Info")
    void setOauth2DetailToUsername(@RequestBody ProviderIdAndNameDto providerInfo,@RequestParam("userName") String userName);


    @PostMapping("/internal/users/register/user")
    SignupResponseDto  CreateNewAccount(@RequestBody RegisterDto registerDto);

    @GetMapping("internal/users/create/user")
    void createUser();
}


