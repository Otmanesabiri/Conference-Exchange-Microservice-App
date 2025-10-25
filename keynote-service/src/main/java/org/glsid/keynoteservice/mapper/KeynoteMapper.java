package org.glsid.keynoteservice.mapper;

import java.util.List;

import org.glsid.keynoteservice.domain.entity.Keynote;
import org.glsid.keynoteservice.dto.KeynoteRequest;
import org.glsid.keynoteservice.dto.KeynoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface KeynoteMapper {

    Keynote toEntity(KeynoteRequest request);

    KeynoteResponse toResponse(Keynote keynote);

    List<KeynoteResponse> toResponseList(List<Keynote> keynotes);

    void updateEntityFromRequest(KeynoteRequest request, @MappingTarget Keynote keynote);
}
