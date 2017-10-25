package com.itbk.service;

import com.itbk.model.GroupStudent;
import com.itbk.repository.GroupStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by PC on 10/25/2017.
 */
@Component
@Service("groupStudentService")
public class GroupStudentServiceImpl implements GroupStudentService {

	@Autowired
	private GroupStudentRepository groupStudentRepository;

	@Override
	public Object findAll() {
		return groupStudentRepository.findAll();
	}

	@Override
	public GroupStudent save(GroupStudent student) {
		return groupStudentRepository.save(student);
	}

	@Override
	public GroupStudent findById(Integer id) {
		return groupStudentRepository.findOne(id);
	}
}
