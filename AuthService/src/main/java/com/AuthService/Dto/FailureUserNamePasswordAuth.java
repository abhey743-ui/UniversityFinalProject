package com.AuthService.Dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FailureUserNamePasswordAuth {
         private  String message;
}
