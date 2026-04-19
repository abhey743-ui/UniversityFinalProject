package com.user.Controller.UserController;
import com.user.Dto.UserDetailsDto.UserDetails;
import com.user.Service.UserService.UserInfoService.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@AllArgsConstructor
public class UserDetailsController {

    private UserInfoService userInfoService;


    @GetMapping("/get/userInfo")
    public ResponseEntity<UserDetails> getUserInfo(){

        UserDetails userDetails =  userInfoService.getAllUserDetails();
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);

    }


    @PatchMapping("update/user")
    public ResponseEntity<UserDetails> updateDetails(@RequestBody UserDetails userDetails){

        UserDetails userDetails1 = userInfoService.updateUserDetails(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails1);

    }
}
