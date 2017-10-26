package com.itbk.repository;

import com.itbk.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 10/26/2017.
 */
@Repository("teacherRepository")
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
	Teacher save(Teacher teacher);
}
