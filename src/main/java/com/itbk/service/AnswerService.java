package com.itbk.service;

import com.itbk.model.Answer;

/**
 * Created by PC on 10/27/2017.
 */
public interface AnswerService {
	Answer saveAnswer(Answer answer);

	void deleteAnswerById(int id);
}
