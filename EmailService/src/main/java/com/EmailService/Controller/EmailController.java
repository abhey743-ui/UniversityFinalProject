package com.EmailService.Controller;

import com.EmailService.Dto.OrderEmailRequest;
import com.EmailService.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {


    @Autowired
    private EmailService emailService;


    @PostMapping("/email/order-confirmation")
    public void  sendOrderConfirmation(@RequestBody OrderEmailRequest request) {
        emailService.sendOrderConfirmation(
                request.getTo(),
                request.getOrderId(),
                request.getAmount()
        );

}}
