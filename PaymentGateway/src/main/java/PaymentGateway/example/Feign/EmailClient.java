package PaymentGateway.example.Feign;


import PaymentGateway.example.Dto.OrderEmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "EMAILSERVICE",
        configuration = PaymentGateway.example.Feign.FeignClientInterceptor.class
)
@Component
public interface EmailClient {

    @PostMapping("/email/order-confirmation")
    void sendOrderConfirmation(@RequestBody OrderEmailRequest request);
}
