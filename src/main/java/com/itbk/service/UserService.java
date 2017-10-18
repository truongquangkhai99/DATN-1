package com.itbk.service;

import com.itbk.model.User;

public interface UserService {
    User findByUserName(String username);
}
