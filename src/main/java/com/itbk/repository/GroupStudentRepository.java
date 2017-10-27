package com.itbk.repository;

import com.itbk.model.GroupStudent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */

@Repository("groupStudentRepository")
public interface GroupStudentRepository extends CrudRepository<GroupStudent, Integer> {
	@SuppressWarnings("unchecked")
	GroupStudent save(GroupStudent student);

	@Query(value="SELECT DISTINCT group_student FROM groups", nativeQuery = true)
	ArrayList<String> findAllGroupId();
}
