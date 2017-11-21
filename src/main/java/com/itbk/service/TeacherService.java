package com.itbk.service;

import com.itbk.model.Teacher;

/**
 * Created by PC on 10/26/2017.
 */
public interface TeacherService {

	Teacher saveTeacher(Teacher teacher);

	Teacher findTeacherByUsername(String userName);

	Teacher findTeacherByName(String name);

	Teacher findGroupIdByUsername(String userName);

	Object findAllTeacher();

	Object countAllTeacher();
}
