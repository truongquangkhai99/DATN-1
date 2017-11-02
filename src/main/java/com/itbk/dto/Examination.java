package com.itbk.dto;

import com.itbk.model.Answer;

import java.util.List;

/**
 * Created by PC on 10/27/2017.
 */

public class Examination {
	int questionId;
	private String question;
	private List<Answer> answers;
	private boolean radio;

	public Examination() {}

	public Examination(String question, List<Answer> answer, boolean radio) {
		this.question = question;
		this.answers = answer;
		this.radio = radio;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answer) {
		this.answers = answer;
	}

	public boolean getRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
}
