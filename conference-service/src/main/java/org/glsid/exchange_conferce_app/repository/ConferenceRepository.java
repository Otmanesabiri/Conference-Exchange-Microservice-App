package org.glsid.exchange_conferce_app.repository;

import org.glsid.exchange_conferce_app.domain.Conference;
import org.glsid.exchange_conferce_app.domain.ConferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    
    List<Conference> findByType(ConferenceType type);
    
    List<Conference> findByDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Conference> findByKeynoteId(Long keynoteId);
    
    List<Conference> findByTitreContainingIgnoreCase(String titre);
}