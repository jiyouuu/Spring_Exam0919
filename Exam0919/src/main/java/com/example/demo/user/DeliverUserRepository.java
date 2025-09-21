package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverUserRepository extends JpaRepository<DeliverUser, Long>{
	Optional<DeliverUser> findByUsername(String username);
	List<DeliverUser> findByRoleNot(UserRole role);  // WHERE role <> 'ADMIN' 조건이 들어감 SQL문이!!
	boolean existsByUsername(String username);
}
