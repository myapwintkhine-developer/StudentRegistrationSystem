package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.Reset;
import com.ojt.studentmanagmentsb.entity.User;


@Repository
public interface ResetRepository extends JpaRepository<Reset, Integer>{
	
	
	Reset findByResettoken(String reset_token);
	
	Reset findByUser(User user);
	
	List<Reset> findByStatusAndUser(String status,User user);
}
