package org.glsid.keynoteservice.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.glsid.keynoteservice.domain.entity.Keynote;
import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.glsid.keynoteservice.exception.KeynoteNotFoundException;
import org.glsid.keynoteservice.mapper.KeynoteMapper;
import org.glsid.keynoteservice.repository.KeynoteRepository;
import org.glsid.keynoteservice.service.KeynoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class KeynoteServiceImpl implements KeynoteService {

    private final KeynoteRepository repository;
    private final KeynoteMapper mapper;

    public KeynoteServiceImpl(KeynoteRepository repository, KeynoteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public KeynoteResponse create(KeynoteRequest request) {
        Keynote keynote = mapper.toEntity(request);
        Keynote saved = repository.save(keynote);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public KeynoteResponse update(Long id, KeynoteRequest request) {
        Keynote keynote = repository.findById(id)
                .orElseThrow(() -> new KeynoteNotFoundException(id));
        mapper.updateEntityFromRequest(request, keynote);
        Keynote updated = repository.save(keynote);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new KeynoteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public KeynoteResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new KeynoteNotFoundException(id));
    }

    @Override
    public KeynoteResponse getByEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(mapper::toResponse)
                .orElseThrow(() -> new KeynoteNotFoundException(email));
    }

    @Override
    public List<KeynoteResponse> search(String query) {
        if (!StringUtils.hasText(query)) {
            return findAll();
        }
        List<Keynote> keynotes = repository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);
        return mapper.toResponseList(keynotes);
    }

    @Override
    public List<KeynoteResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public List<KeynoteResponse> findByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return repository.findByIdIn(ids).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
