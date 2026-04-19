package com.ProductService.ProcuctController;


import com.ProductService.ProductDto.ProductSearchingDto;
import com.ProductService.ProductModel.ProductModel;
import com.ProductService.ProductService.ProductSearchingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@AllArgsConstructor
public class ProductSearchingController {

    private final ProductSearchingService productSearchingService;

    @PostMapping("products/get/products")
    public List<ProductModel> getSearchedProducts(@RequestBody ProductSearchingDto productSearchingDto){
                 return   productSearchingService.searchProducts(productSearchingDto);

    }

}
