package com.itbk.service;

import com.itbk.model.UserRole;
import com.itbk.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole saveUserRole(UserRole userRole) {
		return userRoleRepository.save(userRole);
	}

	@Override
	public ArrayList<String> findByRoleName(String roleName) {
		return userRoleRepository.findByRoleName(roleName);
	}
}