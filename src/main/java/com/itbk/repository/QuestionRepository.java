package com.itbk.repository;

import com.itbk.model.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC on 10/27/2017.
 */
@Repository("questionRepository")
public interface QuestionRepository extends CrudRepository<Question, Integer> {

	Question save(Question question);

	@Query(value="SELECT top 1 * FROM questions ORDER BY id DESC", nativeQuery = true)
	Question findLastest();

	@Query(value="SELECT distinct q FROM Question q join fetch q.answers join fetch q.groups g where g.id = :groupId")
	List<Question> getExaminationByGroupId(@Param("groupId") int groupId);

	@Modifying
	@Transactional
	@Query(value="DELETE from group_question WHERE group_question.group_id = :group_id", nativeQuery = true)
	void deleteAllQuestionByGroupId(@Param("group_id") int groupId);

	@Query(value="SELECT questions.* FROM questions INNER JOIN group_question ON questions.id = question_id INNER JOIN groups ON group_id = groups.id WHERE group_id = :groupId", nativeQuery = true)
	List<Question> findAllQuestionByGroupId(@Param("groupId") int groupId);

}
