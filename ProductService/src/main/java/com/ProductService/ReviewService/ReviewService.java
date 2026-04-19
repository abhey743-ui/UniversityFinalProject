package com.ProductService.ReviewService;


import com.ProductService.ProductDto.AddRatingDto;
import com.ProductService.ProductDto.AuthDetails;
import com.ProductService.ProductDto.ProductReviewDto;
import com.ProductService.ProductDto.UserRatingResponse;
import com.ProductService.ProductModel.ReviewsModel;
import com.ProductService.ProductRepository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

   private final ReviewRepository reviewRepository;

   public List<ProductReviewDto> getAllReviews(String productId){

          return    reviewRepository.findAllByProductId(productId);

   }


   public  void addRating(AddRatingDto addRatingDto){
       AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Long userId = authDetails.getUserId();

              ReviewsModel reviewsModel = new ReviewsModel();
              reviewsModel.setRating(addRatingDto.getRating());
              reviewsModel.setProductId(addRatingDto.getProductId());
              reviewsModel.setComment(addRatingDto.getComment());
              reviewsModel.setUserId(userId);
              reviewRepository.save(reviewsModel);

   }

   public void deleteRating(String ratingId){

            AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = authDetails.getUserId();
            reviewRepository.deleteByIdAndUserId(ratingId, userId);

   }

   @Transactional
   public void updateRating(String  comment,String  ratingId){
       AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Long userId = authDetails.getUserId();

       ReviewsModel reviewsModel = reviewRepository.findAllByIdAndUserId(ratingId,userId);
       reviewsModel.setComment(comment);
       reviewRepository.save(reviewsModel);

   }

   public List<UserRatingResponse> getUserRating(){
       AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Long userId = authDetails.getUserId();
       List<ReviewsModel>  userRatings =  reviewRepository.findAllByUserId(userId);
       List<UserRatingResponse> userRatingResponse = new ArrayList<>();

       for(ReviewsModel r: userRatings){

           UserRatingResponse userRatingResponse1 = new UserRatingResponse();
           userRatingResponse1.setRating(r.getRating());
           userRatingResponse1.setId(r.getId());
           userRatingResponse1.setComment(r.getComment());
           userRatingResponse1.setProductId(r.getProductId());
           userRatingResponse.add(userRatingResponse1);

       }

        return userRatingResponse;

   }
}
