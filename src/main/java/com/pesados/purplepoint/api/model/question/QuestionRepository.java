package com.pesados.purplepoint.api.model.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByLanguage(String language);

}
