package org.glsid.exchange_conferce_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeynoteDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}