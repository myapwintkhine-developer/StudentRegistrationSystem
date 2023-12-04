package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.entity.UserView;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, Integer>{
	@Query("SELECT u FROM UserView u WHERE u.id = :id AND u.username LIKE %:username% AND u.status LIKE %:status%")
    UserView findUserByIdAndUsernameAndStatus(@Param("id")int id, @Param("username")String username, @Param("status")String status);
	
	@Query("SELECT u FROM UserView u WHERE u.id=:id AND u.username LIKE %:username%")
	UserView findUserByIdAndUsername(@Param("id")int id,@Param("username")String username);
	
	@Query("SELECT u FROM UserView u WHERE u.id=:id AND u.status LIKE %:status%")
	UserView findUserByIdAndStatus(@Param("id")int id,@Param("status")String status);
	
	@Query("SELECT u FROM UserView u WHERE u.username LIKE %:username%")
	List<UserView> findUserByUsername(@Param("username")String username);
	
	@Query("SELECT u FROM UserView u WHERE u.username LIKE %:username% AND u.status LIKE %:status%")
	List<UserView> findUserByUsernameAndStatus(@Param("username")String username,@Param("status")String status);
	
	@Query("SELECT u FROM UserView u WHERE u.status LIKE %:status%")
	List<UserView> findUserByStatus(@Param("status")String status);
}
