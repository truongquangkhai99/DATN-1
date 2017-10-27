package com.itbk.repository;

import com.itbk.dto.Examination;
import com.itbk.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by PC on 10/27/2017.
 */
@Repository("questionRepository")
public interface QuestionRepository extends CrudRepository<Question, Integer> {
	Question save(Question question);

	@Query(value="SELECT top 1 * FROM questions ORDER BY id DESC", nativeQuery = true)
	Question findLastest();

//	@Query(value="SELECT q.name, a.answer 1 * FROM questions as q ORDER BY id DESC", nativeQuery = true)
//	ArrayList<Examination> getExaminationByGroupId(String groupId);

}
