package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.Student;




@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	@Query("SELECT s FROM Student s WHERE s.id=:id AND s.studentname LIKE %:studentname%")
	Student findStudentByIdAndStudentname(@Param("id")int id,@Param("studentname")String studentname);
	
	@Query("SELECT s FROM Student s WHERE s.studentname LIKE %:studentname%")
	List<Student> findStudentByStudentname(@Param("studentname")String studentname);
	
	@Query("SELECT s FROM Student s JOIN s.studentcourse sc JOIN sc.course c WHERE c.coursename LIKE %:coursename%")
    List<Student> findStudentByCoursename(@Param("coursename") String coursename);
	
	@Query("SELECT s FROM Student s JOIN s.studentcourse sc JOIN sc.course c WHERE s.id = :id AND c.coursename LIKE %:coursename%")
	    List<Student> findStudentByStudentidAndCoursename(@Param("id") int id,
	                                                      @Param("coursename") String coursename);
	
	@Query("SELECT s FROM Student s JOIN s.studentcourse sc JOIN sc.course c WHERE s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
	    List<Student> findStudentByStudentnameAndCoursename(@Param("studentname") String studentname,
	                                                      @Param("coursename") String coursename);
	
	@Query("SELECT s FROM Student s JOIN s.studentcourse sc JOIN sc.course c WHERE s.id = :id AND s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
	    List<Student> findStudentByStudentidAndNameAndCoursename(@Param("id") int id,
	                                                      @Param("studentname") String studentname,
	                                                      @Param("coursename") String coursename);
	

			
			
	
		
		
		
		
		
		
	
}
