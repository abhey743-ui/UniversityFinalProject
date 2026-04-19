package com.ProductService.ProcuctController;
import com.ProductService.ProductDto.ProductAddingDto;
import com.ProductService.ProductDto.ProductUpdateRequestDto;
import com.ProductService.ProductService.ProductManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ApplicationPath;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
public class ProductManagementController {

    private final ProductManagementService productManagementService;
    @PostMapping(
            value = "/internal-service/add/product",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void addProduct(
            @RequestPart("data") String productJson,
            @RequestPart("images") List<MultipartFile> images
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ProductAddingDto dto = mapper.readValue(productJson, ProductAddingDto.class);

        productManagementService.addProducts(dto, images);
    }




    @GetMapping("/internal-service/delete/product/{id}")
    public void  deleteProduct(@PathVariable String id){

        productManagementService.deleteProduct(id);

    }
    @PostMapping("/internal-service/update/product")
    public void updateProducts(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) throws IOException {

        productManagementService.updateProduct(productUpdateRequestDto);

    }
}
