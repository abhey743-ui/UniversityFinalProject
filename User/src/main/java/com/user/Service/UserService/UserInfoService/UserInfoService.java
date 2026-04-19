package com.user.Service.UserService.UserInfoService;
import com.user.Dto.AuthDto.UserPrincipalAuth;
import com.user.Dto.UserDetailsDto.UserDetails;
import com.user.Entity.User;
import com.user.Repository.UserRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;



@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
     @Transactional
    public UserDetails  getAllUserDetails(){
               UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
               Long id = userPrincipalAuth.getId();

               User userDetailsData =  userRepository.findAllById(id);

                      return  UserDetails.builder()
                              .firstName(userDetailsData.getFirstName()
                              ).lastName(userDetailsData.getLastName())
                              .mobileNumber(userDetailsData.getMobileNumber()).build();
    }
    @Transactional
    public UserDetails updateUserDetails(UserDetails userDetails){
              UserPrincipalAuth userPrincipalAuth = (UserPrincipalAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              Long id = userPrincipalAuth.getId();
              User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User is not found"));

              if(userDetails.getFirstName() !=null){
                  user.setFirstName(userDetails.getFirstName());
              }
              if(userDetails.getLastName() != null){
                  user.setLastName(userDetails.getLastName());
              }
              if(userDetails.getMobileNumber() != null){
                  user.setMobileNumber(userDetails.getMobileNumber());
              }

              return UserDetails.builder().firstName(user.getFirstName()).lastName(user.getLastName()).mobileNumber(user.getMobileNumber()).build();

    }

}
