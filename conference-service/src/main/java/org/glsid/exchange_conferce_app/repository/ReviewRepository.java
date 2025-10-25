package org.glsid.exchange_conferce_app.repository;

import org.glsid.exchange_conferce_app.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByConferenceId(Long conferenceId);
}