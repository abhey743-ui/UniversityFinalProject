package com.ProductService.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class ProductAddingDto {

    private String productName;
    private String brand;
    private String condition;
    private String productType;
    private String category;
    private float price;
    private Map<String, Object> attributes;
}
