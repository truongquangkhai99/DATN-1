package com.itbk.service;

import com.itbk.model.UserRole;

import java.util.ArrayList;

public interface UserRoleService {
	UserRole saveUserRole(UserRole userRole);
	ArrayList<String> findByRoleName(String roleName);
}
