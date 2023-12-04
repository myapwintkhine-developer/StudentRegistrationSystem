package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByStatus(String status);
	
	User findByUsername(String username);
	
	User findByIdAndUsername(int id, String username);
	
	List<User> findByUsernameContaining(String keyword);
	
	User findByUsernameAndEmail(String username, String email);
	
	@Query("SELECT u FROM User u WHERE u.emailtoken = :emailtoken")
    User findByEmailtoken(@Param("emailtoken") String emailtoken);
	
	User findByEmail(String email);
	
	List<User> findByEmailverifiedTrue();
	
	@Query("SELECT u FROM User u WHERE u.id = :id AND u.username LIKE %:username% AND u.status LIKE %:status% AND u.email =:e")
    User findUserByIdAndUsernameAndStatus(@Param("id")int id, @Param("username")String username, @Param("status")String status);
	
	@Query("SELECT u FROM User u WHERE u.id=:id AND u.username LIKE %:username%")
	User findUserByIdAndUsername(@Param("id")int id,@Param("username")String username);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
	List<User> findUserByUsername(@Param("username")String username);
	
	@Query("SELECT u FROM User u WHERE u.id=:id AND u.status LIKE %:status%")
	User findUserByIdAndStatus(@Param("id")int id,@Param("status")String status);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:username% AND u.status LIKE %:status%")
	List<User> findUserByUsernameAndStatus(@Param("username")String username,@Param("status")String status);
	
	@Query("SELECT u FROM User u WHERE u.status LIKE %:status%")
	List<User> findUserByStatus(@Param("status")String status);

}
