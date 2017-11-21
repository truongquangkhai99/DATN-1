package com.itbk.repository;

import com.itbk.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by PC on 10/26/2017.
 */
@Repository("teacherRepository")
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

	Teacher save(Teacher teacher);

	@Query(value="SELECT * FROM teachers where account = :userName", nativeQuery = true)
	Teacher findTeacherByUsername(@Param("userName") String userName);

	@Query(value="SELECT * FROM teachers where name = :name", nativeQuery = true)
	Teacher findTeacherByName(@Param("name") String name);

	@Query(value="SELECT distinct t FROM Teacher t join fetch t.groups where t.account = :userName")
	Teacher findGroupIdByUsername(@Param("userName") String userName);
}
