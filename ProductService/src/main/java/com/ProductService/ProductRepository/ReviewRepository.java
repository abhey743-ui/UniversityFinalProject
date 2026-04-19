package com.ProductService.ProductRepository;

import com.ProductService.ProductDto.ProductReviewDto;
import com.ProductService.ProductModel.ReviewsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository  extends MongoRepository<ReviewsModel,String > {


    List<ProductReviewDto> findAllByProductId(String productId);

    void deleteByIdAndUserId(String ratingId, Long userId);

    ReviewsModel findAllByIdAndUserId(String ratingId, Long userId);


    List<ReviewsModel> findAllByUserId(Long userId);
}
