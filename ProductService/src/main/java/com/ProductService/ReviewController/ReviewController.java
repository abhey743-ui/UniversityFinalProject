package com.ProductService.ReviewController;


import com.ProductService.ProductDto.AddRatingDto;
import com.ProductService.ProductDto.ProductReviewDto;
import com.ProductService.ProductDto.UpdateReviewDto;
import com.ProductService.ProductDto.UserRatingResponse;
import com.ProductService.ReviewService.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/public/get/reviews/{productId}")
    public List<ProductReviewDto> getReview(@PathVariable String productId){

      return   reviewService.getAllReviews(productId);

    }
    @PostMapping("/reviews/private/add/reviews")
    public HttpStatus addReview(@RequestBody AddRatingDto addRatingDto){

         reviewService.addRating(addRatingDto);
         return HttpStatus.CREATED;

    }

    @PutMapping("/reviews/private/update/review/{ratingId}")
    public HttpStatus updateReview(@PathVariable String ratingId,
                                   @RequestBody UpdateReviewDto dto) {

        reviewService.updateRating(dto.getComment(), ratingId);
        return HttpStatus.OK;
    }


    @GetMapping("/reviews/private/delete/review/{ratingId}")
    public HttpStatus deleteReview(@PathVariable String  ratingId){

        reviewService.deleteRating(ratingId);
        return HttpStatus.OK;
    }

    @GetMapping("/reviews/private/get/user/rating")
    public List<UserRatingResponse> getUserProduct(){
           return     reviewService.getUserRating();
    }
}
