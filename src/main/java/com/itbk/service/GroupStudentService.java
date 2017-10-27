package com.itbk.service;

import com.itbk.model.GroupStudent;

import java.util.ArrayList;

/**
 * Created by PC on 10/25/2017.
 */
public interface GroupStudentService {

	Object findAll();

	GroupStudent save(GroupStudent student);

	GroupStudent findById(Integer id);

	ArrayList<String> findAllGroupId();
}
