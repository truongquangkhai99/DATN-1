package com.itbk.repository;

import com.itbk.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {

	User save(User user);

	@Query(value="SELECT * FROM users where username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);

	@Modifying
	@Transactional
	@Query(value="UPDATE users SET username = :username where id = :id", nativeQuery = true)
	int updateUsername(@Param("username") String username, @Param("id") int id);

	@Modifying
	@Transactional
	@Query(value="UPDATE users SET password = :password where id = :id", nativeQuery = true)
	int updatePassword(@Param("password") String password, @Param("id") int id);
}
