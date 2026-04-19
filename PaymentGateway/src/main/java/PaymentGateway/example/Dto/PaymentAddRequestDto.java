package PaymentGateway.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PaymentAddRequestDto {


    private String email;
    private String address;
    private String postCode;
    private String city;
    private String CardHolderName;
    private String expiryDate;
    private String vcc;
    private List<String > productId;
    private Double amount;

}
