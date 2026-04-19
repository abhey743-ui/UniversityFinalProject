package com.ProductService.ProductDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRatingResponse {

    private String  id;

    private  String  productId;

    private String comment;

    private  int rating;
}
