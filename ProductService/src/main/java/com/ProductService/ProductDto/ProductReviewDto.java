package com.ProductService.ProductDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewDto {

    private String  id;

    private  String  productId;

    private String comment;

    private  int rating;
}
