package com.group41.Greenr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group41.Greenr.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	User findByEmail(String email); // 'findByEmail' retrieves user from database by email - is the method naming convention for spring data
	User findByUsername(String username);
}
