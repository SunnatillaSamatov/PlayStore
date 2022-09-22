package com.example.petplaystore.service;

import com.example.petplaystore.payload.ReqReview;
import com.example.petplaystore.payload.Response;

public interface ReviewService {

    Response saveReview(ReqReview reqReview);
    Response updateReview(Long reviewId,ReqReview reqReview);
    Response deleteReview(Long reviewId);
    Response getListReviewOfMobileApp(Long mobileAppId);
    Response getAllReview();
}
