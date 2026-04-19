package PaymentGateway.example.Service;

import PaymentGateway.example.Dto.OrderEmailRequest;
import PaymentGateway.example.Dto.PaymentAddRequestDto;
import PaymentGateway.example.Dto.PaymentAddResponseDto;
import PaymentGateway.example.Entity.OrderDetails;
import PaymentGateway.example.Feign.EmailClient;
import PaymentGateway.example.Repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private OrderDetailsRepository orderDetailsRepository;
    private EmailClient emailClient;

    public void buyProduct(PaymentAddRequestDto request){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCity(request.getCity());
        orderDetails.setAddress(request.getAddress());
        orderDetails.setEmail(request.getEmail());
        orderDetails.setCardHolderName(request.getCardHolderName());
        orderDetails.setExpiryDate(request.getExpiryDate());
        orderDetails.setPostCode(request.getPostCode());
        orderDetails.setVcc(request.getVcc());
        orderDetails.setProductId(request.getProductId());


        OrderDetails updatedOrderDetails  = orderDetailsRepository.save(orderDetails);
        OrderEmailRequest orderEmailRequest = new OrderEmailRequest(updatedOrderDetails.getEmail(),updatedOrderDetails.getId(), request.getAmount());
        emailClient.sendOrderConfirmation(orderEmailRequest);



    }

}
