package com.itbk.service;

import com.itbk.model.User;
import com.itbk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public int updateUsername(String username, int id) {
		return userRepository.updateUsername(username, id);
	}

	@Override
	public int updatePassword(String password, int id) {
		return userRepository.updatePassword(password, id);
	}

}