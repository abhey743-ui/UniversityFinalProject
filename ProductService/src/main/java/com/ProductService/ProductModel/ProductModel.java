package com.ProductService.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;



@Document("Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    @Id
    private String id;

    private String productName;
    private String brand;
    private String color;
    private String condition;
    private String productType;
    private String category;
    private Float price;
    private float rating;
    private int ratingCount;
    private Map<String, Object> attributes;
    private List<String> imageUrls;

}
