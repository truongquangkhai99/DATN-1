package com.itbk.service;

import com.itbk.model.User;

public interface UserService {

	User findByUserName(String username);

	User saveUser(User user);

	int updateUsername(String username, int id);

	int updatePassword(String password, int id);

	int updateEnabled(boolean enabled, int id);

	void deleteUserById(int id);
}
