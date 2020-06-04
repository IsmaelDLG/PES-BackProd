package com.pesados.purplepoint.api.model.definition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {

		List<Definition> findByLanguage(String language);

		Optional<Definition> findByWord(String word);
}
