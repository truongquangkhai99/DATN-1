package com.itbk.dto;

import java.util.ArrayList;

/**
 * Created by PC on 10/27/2017.
 */

public class Examination {
	private String question;
	private ArrayList<String> answer;

	public Examination() {}

	public Examination(String question, ArrayList<String> answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<String> getAnswer() {
		return answer;
	}

	public void setAnswer(ArrayList<String> answer1) {
		this.answer = answer1;
	}
}
