package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;


@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer>{
	List<StudentCourse> findByStudent(Student student);
	
	

}
