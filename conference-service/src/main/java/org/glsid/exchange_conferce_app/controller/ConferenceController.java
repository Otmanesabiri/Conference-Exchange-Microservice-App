package org.glsid.exchange_conferce_app.controller;

import lombok.RequiredArgsConstructor;
import org.glsid.exchange_conferce_app.domain.ConferenceType;
import org.glsid.exchange_conferce_app.dto.ConferenceDTO;
import org.glsid.exchange_conferce_app.service.ConferenceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@RequiredArgsConstructor
public class ConferenceController {
    
    private final ConferenceService conferenceService;
    
    @PostMapping
    public ResponseEntity<ConferenceDTO> createConference(@RequestBody ConferenceDTO conferenceDTO) {
        return new ResponseEntity<>(conferenceService.createConference(conferenceDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConferenceDTO> updateConference(
            @PathVariable Long id, 
            @RequestBody ConferenceDTO conferenceDTO) {
        return ResponseEntity.ok(conferenceService.updateConference(id, conferenceDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConferenceDTO> getConferenceById(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.getConferenceById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ConferenceDTO>> getAllConferences() {
        return ResponseEntity.ok(conferenceService.getAllConferences());
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ConferenceDTO>> getConferencesByType(
            @PathVariable ConferenceType type) {
        return ResponseEntity.ok(conferenceService.getConferencesByType(type));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<ConferenceDTO>> getConferencesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(conferenceService.getConferencesByDateRange(start, end));
    }
    
    @GetMapping("/keynote/{keynoteId}")
    public ResponseEntity<List<ConferenceDTO>> getConferencesByKeynoteId(
            @PathVariable Long keynoteId) {
        return ResponseEntity.ok(conferenceService.getConferencesByKeynoteId(keynoteId));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ConferenceDTO>> searchConferencesByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(conferenceService.searchConferencesByTitle(title));
    }
    
    @PostMapping("/{id}/register")
    public ResponseEntity<ConferenceDTO> registerAttendee(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.registerAttendee(id));
    }
}