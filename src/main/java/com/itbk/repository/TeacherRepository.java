package com.itbk.repository;

import com.itbk.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 10/26/2017.
 */
@Repository("teacherRepository")
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
	Teacher save(Teacher teacher);

	@Query(value="SELECT * FROM teachers where account = :userName", nativeQuery = true)
	Teacher findTeacherByUsername(@Param("userName") String userName);
}
