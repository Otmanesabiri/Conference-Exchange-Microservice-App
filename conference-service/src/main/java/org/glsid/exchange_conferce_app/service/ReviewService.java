package org.glsid.exchange_conferce_app.service;

import org.glsid.exchange_conferce_app.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    
    ReviewDTO createReview(ReviewDTO reviewDTO);
    
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    
    void deleteReview(Long id);
    
    ReviewDTO getReviewById(Long id);
    
    List<ReviewDTO> getReviewsByConferenceId(Long conferenceId);
}