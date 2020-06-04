package com.pesados.purplepoint.api.model.question;


import java.util.List;
import java.util.Optional;


public interface QuestionService {

	List<Question> getQuestionByLanguage(String language);

	Optional<Question> getQuestionByID(Long id);

	void deleteById(Long id);

	Question saveQuestion(Question newQuestion);
}
