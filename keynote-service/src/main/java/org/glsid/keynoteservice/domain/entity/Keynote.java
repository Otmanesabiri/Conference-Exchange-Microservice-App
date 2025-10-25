package org.glsid.keynoteservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "keynotes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Keynote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String nom;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String prenom;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String fonction;

    @Column(length = 255)
    private String organisation;

    @Column(length = 4000)
    private String biographie;

    @Column(length = 255)
    private String photoUrl;
}
