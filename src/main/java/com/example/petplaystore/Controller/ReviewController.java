package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqReview;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.ReviewService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public HttpEntity<?> saveReview(@RequestBody ReqReview reqReview) {
        Response response = reviewService.saveReview(reqReview);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public HttpEntity<?> updateReview(@RequestParam Long reviewId, @RequestBody ReqReview reqReview) {
        Response response = reviewService.updateReview(reviewId, reqReview);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public HttpEntity<?> deleteReview(@RequestParam Long reviewId) {
        Response response = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public HttpEntity<?> getReviewList(@RequestParam Long mobileAppId) {
        Response response = reviewService.getListReviewOfMobileApp(mobileAppId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAllReviewList() {
        Response response = reviewService.getAllReview();
        return ResponseEntity.ok(response);
    }

}
