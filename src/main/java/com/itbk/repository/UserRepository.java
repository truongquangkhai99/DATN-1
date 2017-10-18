package com.itbk.repository;

import com.itbk.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {

//    @Query("from users u left join fetch u.userRole where u.username = ?1")
//    User findByUserName(String username);
}
