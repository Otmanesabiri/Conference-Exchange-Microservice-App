package org.glsid.exchange_conferce_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private LocalDateTime date;
    private String texte;
    private Integer note;
    private Long conferenceId;
}