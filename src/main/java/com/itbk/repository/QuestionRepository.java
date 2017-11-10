package com.itbk.repository;

import com.itbk.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 10/27/2017.
 */
@Repository("questionRepository")
public interface QuestionRepository extends CrudRepository<Question, Integer> {

	Question save(Question question);

	@Query(value="SELECT top 1 * FROM questions ORDER BY id DESC", nativeQuery = true)
	Question findLastest();

	@Query(value="SELECT distinct q FROM Question q join fetch q.answers where q.group = :group")
	List<Question> getExaminationByGroupName(@Param("group") String group);

}
