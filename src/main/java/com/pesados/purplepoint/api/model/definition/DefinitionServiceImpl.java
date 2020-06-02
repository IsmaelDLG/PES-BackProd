package com.pesados.purplepoint.api.model.definition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinitionServiceImpl implements DefinitionService {

	@Autowired
	DefinitionRepository definitionRepository;

	@Override
	public List<Definition> getDefinitionByLanguage(String language) {
		return definitionRepository.findByLanguage(language);
	}

	@Override
	public Definition saveDefinition(Definition newDef) {
		return definitionRepository.save(newDef);
	}

	@Override
	public Optional<Definition> getDefinitionByWord(String word){
		return definitionRepository.findByWord(word);
	}



}
