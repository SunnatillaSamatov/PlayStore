package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.MobileApplication;
import com.example.petplaystore.entity.Review;
import com.example.petplaystore.payload.ReqReview;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.MobileApplicationRepository;
import com.example.petplaystore.repository.ReviewRepository;
import com.example.petplaystore.service.ReviewService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final MobileApplicationRepository mobileApplicationRepository;
    private final ReviewRepository reviewRepository;
    private final JdbcTemplate jdbcTemplate;

    public ReviewServiceImpl(MobileApplicationRepository mobileApplicationRepository, ReviewRepository reviewRepository, JdbcTemplate jdbcTemplate) {
        this.mobileApplicationRepository = mobileApplicationRepository;
        this.reviewRepository = reviewRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response saveReview(ReqReview reqReview) {
        Response response = new Response();
        Review review = new Review();
        Optional<MobileApplication> byId = mobileApplicationRepository.findById(reqReview.getMobileApplicationId());
        if (byId.isPresent()) {
            review.setReview(reqReview.getReview());
            review.setRating(reqReview.getRating());
            review.setDate(reqReview.getDate());
            review.setMobileApplication(byId.get());
            reviewRepository.save(review);
            response.setStatus(new Status(0, "review added"));
        } else {
            response.setStatus(new Status(404, "mobileApp is not present"));
        }
        return response;
    }


    public Response updateReview(Long reviewId, ReqReview reqReview) {
        Response response = new Response();
        Optional<Review> byId = reviewRepository.findById(reviewId);
        if (byId.isPresent()) {
            Review review = byId.get();
            review.setReview(reqReview.getReview());
            review.setRating(reqReview.getRating());
            review.setDate(reqReview.getDate());
            review.setMobileApplication(mobileApplicationRepository.findById(reqReview.getMobileApplicationId()).get());
            reviewRepository.save(review);
            response.setStatus(new Status(0, "review updated"));
        } else {
            response.setStatus(new Status(404, "review is not present"));
        }
        return response;
    }


    public Response deleteReview(Long reviewId) {
        Response response = new Response();
        Optional<Review> byId = reviewRepository.findById(reviewId);
        if (byId.isPresent()) {
            try {
                reviewRepository.deleteById(reviewId);
                response.setStatus(new Status(0, "Review deleted"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object is connected with another object"));
            }
        } else {
            response.setStatus(new Status(404, "Review is not found"));
        }

        return response;
    }

    public Response getListReviewOfMobileApp(Long mobileAppId) {
        Response response = new Response();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ma.id as mobile_application_id, ma.name, r.id, r.review, r.rating, r.date  from review  r left join mobile_application ma on ma.id=r.mobile_application_id where r.mobile_application_id=?", mobileAppId);
        response.setData(maps);
        response.setStatus(new Status(0, "List of Review"));

        return response;
    }


    public Response getAllReview() {
        Response response = new Response();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ma.id as mobile_application_id, ma.name, r.id, r.review, r.rating, r.date  from review  r left join mobile_application ma on ma.id=r.mobile_application_id ");
        response.setData(maps);
        response.setStatus(new Status(0, "All reviewList"));
        return response;
    }

}
