package com.itbk.service;

import com.itbk.model.Question;

/**
 * Created by PC on 10/27/2017.
 */
public interface QuestionService {
	Question saveQuestion(Question question);
	Question findLastest();
}
