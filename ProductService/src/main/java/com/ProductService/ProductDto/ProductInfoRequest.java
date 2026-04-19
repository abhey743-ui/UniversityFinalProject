package com.ProductService.ProductDto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductInfoRequest {

    Set<String> id = new HashSet<>();
}
