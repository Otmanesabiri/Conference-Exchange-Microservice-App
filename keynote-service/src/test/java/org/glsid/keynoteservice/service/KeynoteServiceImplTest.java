package org.glsid.keynoteservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.glsid.keynoteservice.domain.entity.Keynote;
import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.glsid.keynoteservice.exception.KeynoteNotFoundException;
import org.glsid.keynoteservice.mapper.KeynoteMapper;
import org.glsid.keynoteservice.repository.KeynoteRepository;
import org.glsid.keynoteservice.service.impl.KeynoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KeynoteServiceImplTest {

    @Mock
    private KeynoteRepository repository;

    private KeynoteMapper mapper;

    @InjectMocks
    private KeynoteServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(KeynoteMapper.class);
        service = new KeynoteServiceImpl(repository, mapper);
    }

    @Test
    void shouldCreateKeynote() {
        KeynoteRequest request = buildRequest();
        Keynote savedEntity = Keynote.builder()
                .id(1L)
                .nom(request.nom())
                .prenom(request.prenom())
                .email(request.email())
                .fonction(request.fonction())
                .organisation(request.organisation())
                .biographie(request.biographie())
                .photoUrl(request.photoUrl())
                .build();

        when(repository.save(any(Keynote.class))).thenReturn(savedEntity);

        KeynoteResponse response = service.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.email()).isEqualTo(request.email());
        verify(repository, times(1)).save(any(Keynote.class));
    }

    @Test
    void shouldUpdateKeynote() {
        KeynoteRequest request = buildRequest();
        Keynote existing = Keynote.builder()
                .id(1L)
                .nom("Old")
                .prenom("Name")
                .email("old@example.com")
                .fonction("Old Role")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        KeynoteResponse response = service.update(1L, request);

        assertThat(response.email()).isEqualTo(request.email());
        verify(repository).findById(1L);
        verify(repository).save(existing);
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, buildRequest()))
                .isInstanceOf(KeynoteNotFoundException.class);
    }

    @Test
    void shouldDeleteExistingKeynote() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(repository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> service.delete(42L))
                .isInstanceOf(KeynoteNotFoundException.class);
    }

    @Test
    void shouldSearchByQuery() {
        Keynote keynote = Keynote.builder().id(1L).nom("Doe").prenom("John").email("john@example.com").fonction("Role").build();
        when(repository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(eq("doe"), eq("doe")))
                .thenReturn(List.of(keynote));

        List<KeynoteResponse> responses = service.search("doe");

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).nom()).isEqualTo("Doe");
    }

    @Test
    void shouldFindByIds() {
        Keynote keynote = Keynote.builder().id(1L).nom("Doe").prenom("John").email("john@example.com").fonction("Role").build();
        when(repository.findByIdIn(Set.of(1L, 2L))).thenReturn(List.of(keynote));

        List<KeynoteResponse> responses = service.findByIds(Set.of(1L, 2L));

        assertThat(responses).hasSize(1);
        verify(repository).findByIdIn(Set.of(1L, 2L));
    }

    private KeynoteRequest buildRequest() {
        return new KeynoteRequest(
                "Doe",
                "John",
                "john.doe@example.com",
                "Software Architect",
                "Tech Co",
                "Bio",
                "https://example.com/photo.jpg");
    }
}
