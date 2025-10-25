package org.glsid.exchange_conferce_app.controller;

import lombok.RequiredArgsConstructor;
import org.glsid.exchange_conferce_app.dto.ReviewDTO;
import org.glsid.exchange_conferce_app.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id, 
            @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }
    
    @GetMapping("/conference/{conferenceId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByConferenceId(
            @PathVariable Long conferenceId) {
        return ResponseEntity.ok(reviewService.getReviewsByConferenceId(conferenceId));
    }
}