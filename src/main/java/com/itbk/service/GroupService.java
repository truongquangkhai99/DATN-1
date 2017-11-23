package com.itbk.service;

import com.itbk.model.Group;

import java.util.ArrayList;

/**
 * Created by PC on 11/9/2017.
 */
public interface GroupService {

	Group saveGroup(Group group);

	Group findGroupByGroupName(String name);

	Group findGroupById(Integer id);

	ArrayList<Group> findAllGroup();

	Object countAllGroup();

	Object countGroupByTeacherId(Integer idTeacher);

	Object findGroupsByTeacherId(int idTeacher);

	void deleteGroupById(int id);

	void deleteAllGroupByTeacherId(int teacherId);

	void updateGroupName(String name, int id);
}
