package com.itbk.repository;

import com.itbk.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by PC on 11/9/2017.
 */

@Repository("groupRepository")
public interface GroupRepository extends CrudRepository<Group, Integer> {

	Group save(Group group);

	@Query(value="SELECT * FROM groups where name = :name", nativeQuery = true)
	Group findGroupByGroupName(@Param("name") String name);

	@Query(value="SELECT count(*) FROM groups where teacher_id = :id", nativeQuery = true)
	Object countGroupByTeacherId(@Param("id") Integer id);

	@Query(value="SELECT * FROM groups where teacher_id = :idTeacher", nativeQuery = true)
	ArrayList<Group> findGroupsByTeacherId(@Param("idTeacher") int idTeacher);
}
