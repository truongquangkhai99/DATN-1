package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

/**
 * Created by PC on 10/27/2017.
 */

@Entity
@Table(name = "answers")
public class Answer {
	private Integer id;
	private String answer;
	private boolean exactly;
	private Question question;

	public Answer() {}

	public Answer(Integer idQuestion, String answer, boolean exactly) {
		this.answer = answer;
		this.exactly = exactly;
		this.question = new Question();
		this.question.setId(idQuestion);
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

	@Nationalized
	@Column(name = "answer", unique = false, nullable = false, length = 1024)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_question", nullable = false)
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
