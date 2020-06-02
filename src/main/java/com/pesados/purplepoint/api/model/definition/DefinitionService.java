package com.pesados.purplepoint.api.model.definition;


import java.util.List;
import java.util.Optional;

public interface DefinitionService {

	List<Definition> getDefinitionByLanguage(String language);

	Optional<Definition> getDefinitionByWord(String word);

	Definition saveDefinition(Definition newDef);



}
