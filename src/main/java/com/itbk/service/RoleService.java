package com.itbk.service;

import com.itbk.model.Role;

/**
 * Created by PC on 11/13/2017.
 */
public interface RoleService {

	Role saveRole(Role role);

	Role findByName(String name);
}
