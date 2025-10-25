package org.glsid.keynoteservice.service;

import java.util.List;
import java.util.Set;

import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;

public interface KeynoteService {

    KeynoteResponse create(KeynoteRequest request);

    KeynoteResponse update(Long id, KeynoteRequest request);

    void delete(Long id);

    KeynoteResponse getById(Long id);

    KeynoteResponse getByEmail(String email);

    List<KeynoteResponse> search(String query);

    List<KeynoteResponse> findAll();

    List<KeynoteResponse> findByIds(Set<Long> ids);
}
