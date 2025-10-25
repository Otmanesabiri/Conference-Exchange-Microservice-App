package org.glsid.keynoteservice.web.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.glsid.keynoteservice.service.KeynoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = KeynoteController.class)
@AutoConfigureMockMvc(addFilters = false)
class KeynoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KeynoteService keynoteService;

    @Test
    void shouldCreateKeynote() throws Exception {
        KeynoteRequest request = buildRequest();
        KeynoteResponse response = buildResponse(1L, request);

        when(keynoteService.create(any(KeynoteRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/keynotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void shouldUpdateKeynote() throws Exception {
        KeynoteRequest request = buildRequest();
        KeynoteResponse response = buildResponse(1L, request);

        when(keynoteService.update(eq(1L), any(KeynoteRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/keynotes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value(request.nom()));
    }

    @Test
    void shouldDeleteKeynote() throws Exception {
        doNothing().when(keynoteService).delete(1L);

        mockMvc.perform(delete("/api/keynotes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetKeynoteById() throws Exception {
        KeynoteRequest request = buildRequest();
        when(keynoteService.getById(1L)).thenReturn(buildResponse(1L, request));

        mockMvc.perform(get("/api/keynotes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void shouldGetKeynoteByEmail() throws Exception {
        KeynoteRequest request = buildRequest();
        when(keynoteService.getByEmail("john.doe@example.com")).thenReturn(buildResponse(2L, request));

        mockMvc.perform(get("/api/keynotes")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L));
    }

    @Test
    void shouldSearchKeynotes() throws Exception {
        KeynoteRequest request = buildRequest();
        when(keynoteService.search("doe")).thenReturn(List.of(buildResponse(1L, request)));

        mockMvc.perform(get("/api/keynotes/search")
                        .param("query", "doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(request.email()));
    }

    @Test
    void shouldFindAllKeynotes() throws Exception {
        KeynoteRequest request = buildRequest();
        when(keynoteService.findAll()).thenReturn(List.of(buildResponse(1L, request)));

        mockMvc.perform(get("/api/keynotes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldFindKeynotesByIds() throws Exception {
        KeynoteRequest request = buildRequest();
        when(keynoteService.findByIds(Set.of(1L, 2L))).thenReturn(List.of(buildResponse(1L, request)));

        mockMvc.perform(get("/api/keynotes/bulk")
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(request.email()));
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

    private KeynoteResponse buildResponse(Long id, KeynoteRequest request) {
        return new KeynoteResponse(
                id,
                request.nom(),
                request.prenom(),
                request.email(),
                request.fonction(),
                request.organisation(),
                request.biographie(),
                request.photoUrl());
    }
}
