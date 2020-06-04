package com.pesados.purplepoint.api.model.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public List<Question> getQuestionByLanguage(String language) {
		return questionRepository.findByLanguage(language);
	}

	@Override
	public Optional<Question> getQuestionByID(Long id) {
		return questionRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) { questionRepository.deleteById(id); }

	@Override
	public Question saveQuestion(Question newQuestion) {
		return questionRepository.save(newQuestion);
	}


}
