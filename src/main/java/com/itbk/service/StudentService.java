package com.itbk.service;

import com.itbk.model.Student;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */
public interface StudentService {

	Object findAll();

	ArrayList<Student> findAllByGroupId(int groupId);

	Student save(Student student);

	Student findById(Integer id);

	ArrayList<String> findAllGroupId();

	int findGroupIdByUserName(String userName);

	void updateScore(String userName, double score);

	void updateTimerForGroupId(long timer, int groupId);

	void updateIsTested(String userName, boolean isTested);

	void updateTimer(String userName, long timer);

	long findTimerByUsername(String userName);

	boolean findIsTestedByUsername(String userName);
}
