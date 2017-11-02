package com.itbk.service;

import com.itbk.model.Student;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */
public interface StudentService {

	Object findAll();

	ArrayList<Student> findAllByGroup(String group);

	Student save(Student student);

	Student findById(Integer id);

	ArrayList<String> findAllGroupId();

	String findGroupByUserName(String userName);

	ArrayList<String> findGroupByNameTeacher(String teacherName);

	void updateScore(String userName, double score);

	void updateTimerForGroup(long timer, String group);

	void updateIsTested(String userName, boolean isTested);

	void updateTimer(String userName, long timer);

	long findTimerByUsername(String userName);

	boolean findIsTestedByUsername(String userName);
}
