package com.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sport.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);
}
