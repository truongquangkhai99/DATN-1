package com.itbk.service;

import com.itbk.model.Question;
import com.itbk.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PC on 10/27/2017.
 */
@Component
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Question saveQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question findLastest() {
		return questionRepository.findLastest();
	}

	@Override
	public List<Question> getExaminationByGroupId(int groupId) {
		return questionRepository.getExaminationByGroupId(groupId);
	}

	@Override
	public List<Question> findAllQuestionByGroupId(int groupId) {
		return questionRepository.findAllQuestionByGroupId(groupId);
	}

	@Override
	public void deleteQuestionById(int id) {
		questionRepository.delete(id);
	}

	@Override
	public void deleteAllQuestionByGroupId(int groupId) {
		questionRepository.deleteAllQuestionByGroupId(groupId);
	}
}
