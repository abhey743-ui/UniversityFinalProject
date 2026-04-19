package com.ProductService.ProductDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRatingDto {


    private String  productId;
    private int rating;
    private String comment;


}
