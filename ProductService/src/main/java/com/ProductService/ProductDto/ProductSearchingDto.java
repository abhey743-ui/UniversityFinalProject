package com.ProductService.ProductDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductSearchingDto {

    private String productName;
    private String brand;
//    private String color;
    private String condition;
    private String productType;
    private String category;

    private Float minPrice;
    private Float maxPrice;

    private Integer minRating;
    private Integer maxRating;

    private List<String> colors;
    private List<String> brands;

    private Map<String, List<String>> attributes;

    private Integer page = 0;
    private Integer size = 10;

    private String sortBy;
    private String sortDirection;
}
