package org.glsid.keynoteservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KeynoteRequest(
        @NotBlank @Size(max = 80) String nom,
        @NotBlank @Size(max = 80) String prenom,
        @Email @NotBlank @Size(max = 150) String email,
        @NotBlank @Size(max = 120) String fonction,
        @Size(max = 255) String organisation,
        @Size(max = 4000) String biographie,
        @Size(max = 255) String photoUrl) {
}
