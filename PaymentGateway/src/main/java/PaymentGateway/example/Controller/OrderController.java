package PaymentGateway.example.Controller;

import PaymentGateway.example.Dto.PaymentAddRequestDto;
import PaymentGateway.example.Dto.PaymentAddResponseDto;
import PaymentGateway.example.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/buy/product/payment")
    public PaymentAddResponseDto doBuy(@RequestBody PaymentAddRequestDto paymentAddRequestDto){

                    orderService.buyProduct(paymentAddRequestDto);
        return  PaymentAddResponseDto.builder().message("THANKS FOR SHOPPING").build();
    }


}
