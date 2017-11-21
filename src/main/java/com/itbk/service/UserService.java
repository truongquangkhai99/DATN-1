package com.itbk.service;

import com.itbk.model.User;

public interface UserService {

	User findByUserName(String username);

	User saveUser(User user);

	Object updateUsername(String username, int id);

	Object updatePassword(String password, int id);
}
