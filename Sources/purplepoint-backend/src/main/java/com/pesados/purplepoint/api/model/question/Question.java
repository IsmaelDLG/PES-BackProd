package com.pesados.purplepoint.api.model.question;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "Question")
public class Question {
	@Schema(description = "Id of the definition.", required = true)
	private @Id Long questionId;

	@Schema(description = "Question", example="Question", required = true)
	@Column(name = "question", length = 2500)
	private String question;
	@Schema(description = "Answer to the question", example="Answer of the question", required = true)
	@Column(name = "answer", length = 2500)
	private String answer;
	@Schema(description = "List of votes", example=" List of users emails voters", required = true)
	@Column(name = "upvotes")
	private long numUpvotes;
	@Schema(description = "List of downvotes", example=" List of users emails downvoters", required = true)
	@Column(name = "downvotes")
	private long numDownvotes;
	@Schema(description = "Language of the definition.", example="esp", required = true)
	private String language;

	public  Question() {

	}

	public Question(String question, String answer, String language) {
		this.question = question;
		this.answer = answer;
		this.language = language;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public long getNumUpvotes() {
		return numUpvotes;
	}

	public void setNumUpvotes(long numUpvotes) { this.numUpvotes = numUpvotes; }

	public long getNumDownvotes() {
		return numDownvotes;
	}

	public void setNumDownvotes(long numDownvotes) {
		this.numDownvotes = numDownvotes;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
