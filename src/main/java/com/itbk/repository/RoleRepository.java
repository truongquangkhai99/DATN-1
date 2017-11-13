package com.itbk.repository;

import com.itbk.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 11/13/2017.
 */
@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Integer> {

		Role findByName(String name);

}