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

	@Query(value="UPDATE users SET username = :username where group_id = :id", nativeQuery = true)
	Object updateUsername(@Param("username") String username, @Param("id") int id);

	@Query(value="UPDATE users SET timer = :password where group_id = :id", nativeQuery = true)
	Object updatePassword(@Param("password") String password, @Param("id") int id);
}
