package com.EmailService.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderEmailRequest {

    private String to;
    private Long orderId;
    private Double  amount;

}
