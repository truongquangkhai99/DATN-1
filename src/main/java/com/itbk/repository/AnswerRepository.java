package com.itbk.repository;

import com.itbk.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 10/27/2017.
 */
@Repository("answerRepository")
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
	Answer save(Answer answer);
}
