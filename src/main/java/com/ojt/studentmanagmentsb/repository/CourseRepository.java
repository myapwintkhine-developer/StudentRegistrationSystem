package com.ojt.studentmanagmentsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.Course;



@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
      Course findByCoursename(String cname);
      
      
}
