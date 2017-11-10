package com.itbk.service;

import com.itbk.model.Group;
import com.itbk.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
	public Group findGroupById(int id) {
		return groupRepository.findOne(id);
	}
}
