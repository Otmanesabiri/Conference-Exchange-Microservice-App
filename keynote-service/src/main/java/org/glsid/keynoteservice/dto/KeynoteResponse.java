package org.glsid.keynoteservice.dto;

public record KeynoteResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String fonction,
        String organisation,
        String biographie,
        String photoUrl) {
}
