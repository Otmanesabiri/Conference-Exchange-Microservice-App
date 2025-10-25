package org.glsid.exchange_conferce_app.service;

import org.glsid.exchange_conferce_app.dto.ConferenceDTO;
import org.glsid.exchange_conferce_app.domain.ConferenceType;

import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceService {
    
    ConferenceDTO createConference(ConferenceDTO conferenceDTO);
    
    ConferenceDTO updateConference(Long id, ConferenceDTO conferenceDTO);
    
    void deleteConference(Long id);
    
    ConferenceDTO getConferenceById(Long id);
    
    List<ConferenceDTO> getAllConferences();
    
    List<ConferenceDTO> getConferencesByType(ConferenceType type);
    
    List<ConferenceDTO> getConferencesByDateRange(LocalDateTime start, LocalDateTime end);
    
    List<ConferenceDTO> getConferencesByKeynoteId(Long keynoteId);
    
    List<ConferenceDTO> searchConferencesByTitle(String title);
    
    ConferenceDTO registerAttendee(Long id);
}