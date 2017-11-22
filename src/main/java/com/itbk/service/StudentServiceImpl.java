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
@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Object findAll() {
		return studentRepository.findAll();
	}

	@Override
	public ArrayList<Student> findAllByGroupId(int groupId) {
		return studentRepository.findAllByGroupId(groupId);
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
	public int findGroupIdByUserName(String userName) {
		return studentRepository.findGroupIdByUserName(userName);
	}

	@Override
	public void updateScore(String userName, double score) {
		studentRepository.updateScore(userName, score);
	}

	@Override
	public void updateTimerForGroupId(long timer, int groupId) {
		studentRepository.updateTimerForGroupId(timer, groupId);
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
	public Object findIsTestedByUsername(String userName) {
		return studentRepository.findIsTestedByUsername(userName);
	}

	@Override
	public Object countAllStudent() {
		return studentRepository.count();
	}

	@Override
	public Object countStudentByGroupId(int idGroup) {
		return studentRepository.countStudentByGroupId(idGroup);
	}

	@Override
	public void deleteStudentById(int id) {
		studentRepository.delete(id);
	}

	@Override
	public void deleteAllStudentByGroupId(int groupId) {
		studentRepository.deleteAllStudentByGroupId(groupId);
	}
}
