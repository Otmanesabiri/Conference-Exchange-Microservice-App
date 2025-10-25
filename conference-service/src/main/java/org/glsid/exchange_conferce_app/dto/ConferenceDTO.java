package org.glsid.exchange_conferce_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.glsid.exchange_conferce_app.domain.ConferenceType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceDTO {
    private Long id;
    private String titre;
    private ConferenceType type;
    private LocalDateTime date;
    private Integer duree;
    private Integer nombreInscrits;
    private Double score;
    private Long keynoteId;
    private KeynoteDTO keynote; // To be populated when fetching from keynote service
}