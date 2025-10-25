package org.glsid.keynoteservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.glsid.keynoteservice.domain.entity.Keynote;
import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class KeynoteMapperTest {

    private KeynoteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(KeynoteMapper.class);
    }

    @Test
    void shouldMapRequestToEntity() {
        KeynoteRequest request = new KeynoteRequest(
                "Doe",
                "John",
                "john.doe@example.com",
                "Software Architect",
                "Tech Co",
                "Bio",
                "https://example.com/photo.jpg");

        Keynote entity = mapper.toEntity(request);

        assertThat(entity.getNom()).isEqualTo(request.nom());
        assertThat(entity.getPrenom()).isEqualTo(request.prenom());
        assertThat(entity.getEmail()).isEqualTo(request.email());
    }

    @Test
    void shouldMapEntityToResponse() {
        Keynote entity = Keynote.builder()
                .id(1L)
                .nom("Doe")
                .prenom("Jane")
                .email("jane.doe@example.com")
                .fonction("Developer Advocate")
                .organisation("DevRel Inc")
                .biographie("Bio")
                .photoUrl("https://example.com/photo.jpg")
                .build();

        KeynoteResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.email()).isEqualTo(entity.getEmail());
        assertThat(response.prenom()).isEqualTo(entity.getPrenom());
    }
}
