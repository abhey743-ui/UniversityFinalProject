package com.ProductService.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Document("product_reviews")
@Getter
@Setter
public class ReviewsModel {


    @Id
    private String  id;

    private String  productId;

    private Long userId;

    private String comment;

    private  int rating;

}
