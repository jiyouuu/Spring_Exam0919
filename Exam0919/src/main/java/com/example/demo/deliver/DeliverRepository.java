package com.example.demo.deliver;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverRepository extends JpaRepository<Deliver, Long>{

	List<Deliver> findByuserId(Long id);

	// 키워드로 찾기.. 
	Page<Deliver> findByKeyword(String lower_keyword, Pageable pageable);
}
