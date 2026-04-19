package com.ProductService.ProductService;
import com.ProductService.ProductDto.ProductInfoRequest;
import com.ProductService.ProductDto.ProductInfoResponse;
import com.ProductService.ProductModel.ProductModel;
import com.ProductService.ProductRepository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BasketProductService {

    private final ProductRepository productRepository;

    public List<ProductInfoResponse> getProducts(ProductInfoRequest productInfoRequest){

        List<ProductInfoResponse> products = new ArrayList<>();

        Set<String> ids = productInfoRequest.getId();

        List<ProductModel> productModels = productRepository.findByIdIn(ids);


        for (ProductModel p : productModels) {

            ProductInfoResponse productInfoResponse = ProductInfoResponse.builder()
                    .productId(p.getId())
                    .price(p.getPrice())
                    .productName(p.getProductName())
                    .productType(p.getProductType())
                    .images(p.getImageUrls())
                    .build();

            products.add(productInfoResponse);
        }

        return products;
    }

}
