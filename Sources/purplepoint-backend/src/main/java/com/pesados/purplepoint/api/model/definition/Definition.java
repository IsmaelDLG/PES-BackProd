package com.pesados.purplepoint.api.model.definition;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;


@Entity
@Table(name = "Definitions")
public class Definition {
	@Schema(description = "Id of the definition.", required = true)
	private @Id
	@GeneratedValue
	Long definitionId;

	@Schema(description = "Name of the definition.", example="harassment", required = true)
	@Column(name = "word", length = 2500)
	private String word;
	@Schema(description = "Definition of the word", example="Sexual harassment is any form of unwelcome" +
			" sexual behaviour that’s offensive, humiliating or intimidating.", required = true)
	@Column(name = "definition", length = 2500)
	private String definition;
	@Schema(description = "Example of use of the word", example="Many girls are victims of " +
			"sexual harassment and violence inside and outside of school.", required = true)
	@Column(name = "example", length = 2500)
	private String example;

	@Schema(description = "Word kind", example="verb, noun, adjective", required = true)
	private String kind;
	@Schema(description = "Language of the definition.", example="ESP", required = true)
	private String language;

	public Definition(){}

	public Definition(String word, String definition, String example, String kind, String language) {
		this.word = word;
		this.definition = definition;
		this.example = example;
		this.kind = kind;
		this.language = language;
	}

	public Long getId() {
		return definitionId;
	}

	public void setId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
