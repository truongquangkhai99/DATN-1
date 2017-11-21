package com.itbk.service;

import com.itbk.model.Group;
import com.itbk.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by PC on 11/9/2017.
 */

@Component
@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public Group findGroupByGroupName(String name) {
		return groupRepository.findGroupByGroupName(name);
	}

	@Override
	public Group findGroupById(Integer id) {
		return groupRepository.findOne(id);
	}

	@Override
	public ArrayList<Group> findAllGroup() {
		return (ArrayList<Group>)groupRepository.findAll();
	}

	@Override
	public Object countAllGroup() {
		return groupRepository.count();
	}

	@Override
	public Object countGroupByTeacherId(Integer idTeacher) {
		return groupRepository.countGroupByTeacherId(idTeacher);
	}

	@Override
	public Object findGroupsByTeacherId(int idTeacher) {
		return groupRepository.findGroupsByTeacherId(idTeacher);
	}

	@Override
	public void deleteGroupById(int id) {
		groupRepository.delete(id);
	}
}
