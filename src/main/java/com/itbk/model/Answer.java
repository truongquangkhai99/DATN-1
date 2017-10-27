package com.itbk.model;

import javax.persistence.*;

/**
 * Created by PC on 10/27/2017.
 */

@Entity
@Table(name = "answers")
public class Answer {
	private Integer id;
	private Integer idQuestion;
	private String answer;
	private boolean exactly;

	public Answer() {}

	public Answer(Integer idQuestion, String answer, boolean exactly) {
		this.idQuestion = idQuestion;
		this.answer = answer;
		this.exactly = exactly;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "id_question", unique = false, nullable = false)
	public Integer getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	@Column(name = "answer", unique = false, nullable = false)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "exactly", nullable = false)
	public boolean isExactly() {
		return exactly;
	}

	public void setExactly(boolean exactly) {
		this.exactly = exactly;
	}
}
