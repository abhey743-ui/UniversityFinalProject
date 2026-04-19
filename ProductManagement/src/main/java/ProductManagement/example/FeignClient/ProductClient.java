package ProductManagement.example.FeignClient;
import ProductManagement.example.ProductManagementDto.ProductInfoAddDto;
import ProductManagement.example.ProductManagementDto.ProductUpdateRequestDto;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Component
@FeignClient(
        name = "PRODUCTSERVICE",
        configuration = ProductManagement.example.FeignConfiguration.FeignConfiguration.class
)
public interface ProductClient {

    @PostMapping(
            value = "/internal-service/add/product",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    void addProduct(
            @RequestPart("data") String productJson,
            @RequestPart("images") List<MultipartFile> images
    );

    @GetMapping("/internal-service/delete/product/{id}")
    void deleteProduct(@PathVariable("id") String id);

    @PostMapping(
            value = "/internal-service/update/product",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto);
}


