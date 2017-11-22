package com.itbk.repository;

import com.itbk.model.Answer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PC on 10/27/2017.
 */
@Repository("answerRepository")
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
	Answer save(Answer answer);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM answers WHERE id_question = :questionID", nativeQuery = true)
	void deleteAllAnswerByQuestionId(@Param("questionID") int questionID);
}
