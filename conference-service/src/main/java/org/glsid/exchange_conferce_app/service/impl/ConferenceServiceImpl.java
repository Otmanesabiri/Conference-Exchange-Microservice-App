package org.glsid.exchange_conferce_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.glsid.exchange_conferce_app.client.KeynoteClient;
import org.glsid.exchange_conferce_app.domain.Conference;
import org.glsid.exchange_conferce_app.domain.ConferenceType;
import org.glsid.exchange_conferce_app.dto.ConferenceDTO;
import org.glsid.exchange_conferce_app.dto.KeynoteDTO;
import org.glsid.exchange_conferce_app.repository.ConferenceRepository;
import org.glsid.exchange_conferce_app.service.ConferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConferenceServiceImpl implements ConferenceService {
    
    private final ConferenceRepository conferenceRepository;
    private final KeynoteClient keynoteClient;
    
    @Override
    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) {
        Conference conference = mapToEntity(conferenceDTO);
        conference.setNombreInscrits(0);
        conference.setScore(0.0);
        Conference savedConference = conferenceRepository.save(conference);
        return mapToDTO(savedConference);
    }
    
    @Override
    public ConferenceDTO updateConference(Long id, ConferenceDTO conferenceDTO) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found with id: " + id));
        
        conference.setTitre(conferenceDTO.getTitre());
        conference.setType(conferenceDTO.getType());
        conference.setDate(conferenceDTO.getDate());
        conference.setDuree(conferenceDTO.getDuree());
        conference.setKeynoteId(conferenceDTO.getKeynoteId());
        
        Conference updatedConference = conferenceRepository.save(conference);
        return mapToDTO(updatedConference);
    }
    
    @Override
    public void deleteConference(Long id) {
        conferenceRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ConferenceDTO getConferenceById(Long id) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found with id: " + id));
        return mapToDTO(conference);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> getAllConferences() {
        return conferenceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> getConferencesByType(ConferenceType type) {
        return conferenceRepository.findByType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> getConferencesByDateRange(LocalDateTime start, LocalDateTime end) {
        return conferenceRepository.findByDateBetween(start, end).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> getConferencesByKeynoteId(Long keynoteId) {
        return conferenceRepository.findByKeynoteId(keynoteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> searchConferencesByTitle(String title) {
        return conferenceRepository.findByTitreContainingIgnoreCase(title).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ConferenceDTO registerAttendee(Long id) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found with id: " + id));
        
        conference.setNombreInscrits(conference.getNombreInscrits() + 1);
        Conference updatedConference = conferenceRepository.save(conference);
        
        return mapToDTO(updatedConference);
    }
    
    // Helper methods to map between entity and DTO
    private Conference mapToEntity(ConferenceDTO dto) {
        Conference conference = new Conference();
        conference.setId(dto.getId());
        conference.setTitre(dto.getTitre());
        conference.setType(dto.getType());
        conference.setDate(dto.getDate());
        conference.setDuree(dto.getDuree());
        conference.setNombreInscrits(dto.getNombreInscrits());
        conference.setScore(dto.getScore());
        conference.setKeynoteId(dto.getKeynoteId());
        return conference;
    }
    
    private ConferenceDTO mapToDTO(Conference entity) {
        ConferenceDTO dto = new ConferenceDTO();
        dto.setId(entity.getId());
        dto.setTitre(entity.getTitre());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());
        dto.setDuree(entity.getDuree());
        dto.setNombreInscrits(entity.getNombreInscrits());
        dto.setScore(entity.getScore());
        dto.setKeynoteId(entity.getKeynoteId());
        
        // Try to fetch keynote info if keynoteId is present
        if (entity.getKeynoteId() != null) {
            try {
                KeynoteDTO keynote = keynoteClient.getKeynoteById(entity.getKeynoteId());
                dto.setKeynote(keynote);
            } catch (Exception e) {
                // Log the exception but don't fail the entire operation
                System.err.println("Error fetching keynote: " + e.getMessage());
            }
        }
        
        return dto;
    }
}