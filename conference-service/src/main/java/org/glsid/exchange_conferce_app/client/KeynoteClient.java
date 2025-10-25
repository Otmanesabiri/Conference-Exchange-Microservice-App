package org.glsid.exchange_conferce_app.client;

import org.glsid.exchange_conferce_app.dto.KeynoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "keynote-service")
public interface KeynoteClient {
    
    @GetMapping("/api/keynotes/{id}")
    KeynoteDTO getKeynoteById(@PathVariable("id") Long id);
}