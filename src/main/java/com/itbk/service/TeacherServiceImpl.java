package com.itbk.service;

import com.itbk.model.Teacher;
import com.itbk.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by PC on 10/26/2017.
 */

@Component
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public Teacher saveTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	@Override
	public Teacher findTeacherByUsername(String userName) {
		return teacherRepository.findTeacherByUsername(userName);
	}

	@Override
	public Teacher findGroupIdByUsername(String userName) {
		return teacherRepository.findGroupIdByUsername(userName);
	}
}
