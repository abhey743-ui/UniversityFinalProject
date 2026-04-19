package ProductManagement.example.Service;
import ProductManagement.example.FeignClient.ProductClient;
import ProductManagement.example.ProductManagementDto.ProductInfoAddDto;
import ProductManagement.example.ProductManagementDto.ProductUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductManagementService {

    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    public void addProduct(String data , List<MultipartFile> multipartFiles){

        try {

            productClient.addProduct(data, multipartFiles);

        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize product data", e);
        }
    }

    public void deleteProduct(String id){
        productClient.deleteProduct(id);
    }

    public void updateProduct(ProductUpdateRequestDto productUpdateRequestDto){
                productClient.updateProduct(productUpdateRequestDto);
    }
}
