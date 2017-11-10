package com.itbk.repository;

import com.itbk.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("userRoleRepository")
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

	UserRole save(UserRole userRole);

	@Query(value="SELECT role FROM user_roles where role = :roleName", nativeQuery = true)
	ArrayList<String> findByRoleName(@Param("roleName") String roleName);
}
