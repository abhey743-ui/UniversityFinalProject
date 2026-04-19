package PaymentGateway.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderEmailRequest {
    private String to;
    private Long  orderId;
    private Double  amount;

}
