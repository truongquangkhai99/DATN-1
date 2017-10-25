package com.itbk.repository;

import com.itbk.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRoleRepository")
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
	UserRole save(UserRole userRole);
}
