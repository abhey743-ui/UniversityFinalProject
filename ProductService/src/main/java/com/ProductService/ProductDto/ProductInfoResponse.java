package com.ProductService.ProductDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductInfoResponse {

    private String productId;
    private String productName;
    private float price;
    private String  productType;
    private List<String> images;
}
