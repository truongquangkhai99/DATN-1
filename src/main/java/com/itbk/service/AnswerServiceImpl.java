package com.itbk.service;

import com.itbk.model.Answer;
import com.itbk.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by PC on 10/27/2017.
 */

@Component
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	AnswerRepository answerRepository;

	@Override
	public Answer saveAnswer(Answer answer) {
		return answerRepository.save(answer);
	}
}
