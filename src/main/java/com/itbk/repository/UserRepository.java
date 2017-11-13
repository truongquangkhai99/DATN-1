package com.itbk.repository;

import com.itbk.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {

	User save(User user);

	@Query(value="SELECT * FROM users where username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);
}
