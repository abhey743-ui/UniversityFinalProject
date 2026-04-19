package com.ProductService.ProcuctController.ProductBasketFeignController;


import com.ProductService.ProductDto.ProductInfoRequest;
import com.ProductService.ProductDto.ProductInfoResponse;
import com.ProductService.ProductService.BasketProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BasketFeignController {

           private final BasketProductService basketProductService;


           @PostMapping("/internal/get/product/info")
           public  List<ProductInfoResponse> getProductInfo(@RequestBody  ProductInfoRequest productInfoRequest){


              List<ProductInfoResponse> productInfoResponse = basketProductService.getProducts(productInfoRequest);
              return  productInfoResponse;

           }
}
