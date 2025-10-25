package org.glsid.keynoteservice.web.rest;

import java.util.List;
import java.util.Set;

import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.glsid.keynoteservice.service.KeynoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keynotes")
public class KeynoteController {

    private final KeynoteService keynoteService;

    public KeynoteController(KeynoteService keynoteService) {
        this.keynoteService = keynoteService;
    }

    @PostMapping
    public ResponseEntity<KeynoteResponse> create(@Validated @RequestBody KeynoteRequest request) {
        KeynoteResponse created = keynoteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeynoteResponse> update(@PathVariable Long id, @Validated @RequestBody KeynoteRequest request) {
        KeynoteResponse updated = keynoteService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        keynoteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeynoteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(keynoteService.getById(id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<KeynoteResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(keynoteService.getByEmail(email));
    }

    @GetMapping("/search")
    public ResponseEntity<List<KeynoteResponse>> search(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(keynoteService.search(query));
    }

    @GetMapping
    public ResponseEntity<List<KeynoteResponse>> findAll() {
        return ResponseEntity.ok(keynoteService.findAll());
    }

    @GetMapping("/bulk")
    public ResponseEntity<List<KeynoteResponse>> findByIds(@RequestParam("ids") Set<Long> ids) {
        return ResponseEntity.ok(keynoteService.findByIds(ids));
    }
}
