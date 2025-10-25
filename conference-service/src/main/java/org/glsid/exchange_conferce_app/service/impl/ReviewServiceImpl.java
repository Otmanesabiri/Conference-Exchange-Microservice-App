package org.glsid.exchange_conferce_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.glsid.exchange_conferce_app.domain.Conference;
import org.glsid.exchange_conferce_app.domain.Review;
import org.glsid.exchange_conferce_app.dto.ReviewDTO;
import org.glsid.exchange_conferce_app.repository.ConferenceRepository;
import org.glsid.exchange_conferce_app.repository.ReviewRepository;
import org.glsid.exchange_conferce_app.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ConferenceRepository conferenceRepository;
    
    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Conference conference = conferenceRepository.findById(reviewDTO.getConferenceId())
                .orElseThrow(() -> new RuntimeException("Conference not found with id: " + reviewDTO.getConferenceId()));
        
        Review review = new Review();
        review.setDate(LocalDateTime.now());
        review.setTexte(reviewDTO.getTexte());
        review.setNote(reviewDTO.getNote());
        review.setConference(conference);
        
        Review savedReview = reviewRepository.save(review);
        
        // Update conference score
        conference.getReviews().add(savedReview);
        conference.calculateScore();
        conferenceRepository.save(conference);
        
        return mapToDTO(savedReview);
    }
    
    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        
        review.setTexte(reviewDTO.getTexte());
        review.setNote(reviewDTO.getNote());
        
        Review updatedReview = reviewRepository.save(review);
        
        // Recalculate conference score
        Conference conference = updatedReview.getConference();
        conference.calculateScore();
        conferenceRepository.save(conference);
        
        return mapToDTO(updatedReview);
    }
    
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        
        Conference conference = review.getConference();
        conference.getReviews().remove(review);
        
        reviewRepository.deleteById(id);
        
        // Recalculate conference score
        conference.calculateScore();
        conferenceRepository.save(conference);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        return mapToDTO(review);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByConferenceId(Long conferenceId) {
        return reviewRepository.findByConferenceId(conferenceId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // Helper method to map entity to DTO
    private ReviewDTO mapToDTO(Review entity) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setTexte(entity.getTexte());
        dto.setNote(entity.getNote());
        dto.setConferenceId(entity.getConference().getId());
        return dto;
    }
}