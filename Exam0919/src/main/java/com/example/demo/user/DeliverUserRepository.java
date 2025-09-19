package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverUserRepository extends JpaRepository<DeliverUser, Long>{
	Optional<DeliverUser> findByUsername(String username);
	
	boolean existsByUsername(String username);
}
