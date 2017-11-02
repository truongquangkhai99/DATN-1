package com.itbk.service;

import com.itbk.model.Student;
import com.itbk.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */
@Component
@Service("groupStudentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Object findAll() {
		return studentRepository.findAll();
	}

	@Override
	public ArrayList<Student> findAllByGroup(String group) {
		return studentRepository.findAllByGroup(group);
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student findById(Integer id) {
		return studentRepository.findOne(id);
	}

	@Override
	public ArrayList<String> findAllGroupId() {
		return studentRepository.findAllGroupId();
	}

	@Override
	public String findGroupByUserName(String userName) {
		return studentRepository.findGroupByUserName(userName);
	}

	@Override
	public ArrayList<String> findGroupByNameTeacher(String teacherName) {
		return studentRepository.findGroupByNameTeacher(teacherName);
	}

	@Override
	public void updateScore(String userName, double score) {
		studentRepository.updateScore(userName, score);
	}

	@Override
	public void updateTimerForGroup(long timer, String group) {
		studentRepository.updateTimerForGroup(timer, group);
	}

	@Override
	public void updateIsTested(String userName, boolean isTested) {
		studentRepository.updateIsTested(userName, isTested);
	}

	@Override
	public void updateTimer(String userName, long timer) {
		studentRepository.updateTimer(userName, timer);
	}

	@Override
	public long findTimerByUsername(String userName) {
		return studentRepository.findTimerByUsername(userName);
	}

	@Override
	public boolean findIsTestedByUsername(String userName) {
		return studentRepository.findIsTestedByUsername(userName);
	}
}
