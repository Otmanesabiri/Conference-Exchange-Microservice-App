package org.glsid.keynoteservice.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.glsid.keynoteservice.domain.entity.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeynoteRepository extends JpaRepository<Keynote, Long> {

    Optional<Keynote> findByEmailIgnoreCase(String email);

    List<Keynote> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

    List<Keynote> findByIdIn(Collection<Long> ids);
}
