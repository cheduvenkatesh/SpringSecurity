package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
