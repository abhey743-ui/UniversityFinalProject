package com.user.Controller.FeignController;

import com.user.Dto.AuthDto.*;
import com.user.Service.FeingService.AuthFeign;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
public class FeignController {


    @GetMapping("/get/info/{userName}")
    Oauth2Info getInfo(@PathVariable String userName){
        return authFeign.getInfo(userName);
    }

    private final AuthFeign authFeign;

    public FeignController(AuthFeign authFeign) {
        this.authFeign = authFeign;
    }

    @GetMapping("get/userInfo/{userName}")
    UserInfo getUserInfo(@PathVariable String userName){

         return authFeign.getUserInfo(userName);
    };


   @PostMapping("/get/OauthInfo")
   Oauth2Info getOauth2Info(@RequestBody Oauth2Info oauth2Info){

           return authFeign.getOauth2LoginInfo(oauth2Info);

    };

    @PostMapping("/set/auth2/info")
    void setOauth2Info(@RequestBody Oauth2Info oauth2Info){

          authFeign.setOauth2LoginInfo(oauth2Info);

    };

    @GetMapping("/get/userInfo/for/Token/{userName}")
    UserInfoForToken getUserInfoForToken(@PathVariable("userName") String userName){

        return authFeign.getUserInfoForToken(userName);

    };

    @PostMapping("set/provider/Info")
    void setOauth2DetailToUsername(@RequestBody ProviderIdAndUsername providerInfo, @RequestParam("userName") String userName){

         authFeign.setProviderIdAndUserName(providerInfo,userName);
    }

    @PostMapping("/register/user")
    public SignupResponse signup(@RequestBody RegisterDto registerDto) {

        return authFeign.signup(registerDto);

    }
}
