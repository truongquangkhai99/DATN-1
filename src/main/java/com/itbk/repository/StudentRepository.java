package com.itbk.repository;

import com.itbk.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */

@Repository("groupStudentRepository")
public interface StudentRepository extends CrudRepository<Student, Integer> {
	@SuppressWarnings("unchecked")
	Student save(Student student);

	@Query(value="SELECT * FROM students where groups = :group", nativeQuery = true)
	ArrayList<Student> findAllByGroup(@Param("group") String group);

	@Query(value="SELECT DISTINCT groups FROM students", nativeQuery = true)
	ArrayList<String> findAllGroupId();

	@Query(value="SELECT DISTINCT groups FROM students where idb = :userName", nativeQuery = true)
	String findGroupByUserName(@Param("userName") String userName);

	@Modifying
	@Transactional
	@Query(value="UPDATE students SET score = :score where idb = :userName", nativeQuery = true)
	int updateScore(@Param("userName") String userName, @Param("score") double score);

	@Modifying
	@Transactional
	@Query(value="UPDATE students SET is_tested = :isTested where idb = :userName", nativeQuery = true)
	int updateIsTested(@Param("userName") String userName, @Param("isTested") boolean isTested);

	@Modifying
	@Transactional
	@Query(value="UPDATE students SET timer = :timer where idb = :userName", nativeQuery = true)
	int updateTimer(@Param("userName") String userName, @Param("timer") long timer);

	@Query(value="SELECT is_tested FROM students where idb = :userName", nativeQuery = true)
	boolean findIsTestedByUsername(@Param("userName") String userName);

	@Query(value="SELECT DISTINCT groups FROM students where teacher = :teacherName", nativeQuery = true)
	ArrayList<String> findGroupByNameTeacher(@Param("teacherName") String teacherName);

	@Modifying
	@Transactional
	@Query(value="UPDATE students SET timer = :timer where groups = :group", nativeQuery = true)
	void updateTimerForGroup(@Param("timer") long timer, @Param("group") String group);

	@Query(value="SELECT timer FROM students where idb = :userName", nativeQuery = true)
	long findTimerByUsername(@Param("userName") String userName);

}
