package com.ojt.studentmanagmentsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.repository.CourseRepository;



@Service
public class CourseService {
       @Autowired CourseRepository courserepo;
       
       public boolean createCourse(Course course) {
       	Course savedCourse = courserepo.save(course);
           return savedCourse != null;
       }
       
       public boolean updateCourse(Course course) {
    	   Course updatedCourse=courserepo.save(course);
    	   return updatedCourse != null;
       }
       
       public List<Course> getAllCourses(){
    	   return courserepo.findAll();
       }
       
       public Course getCourseByCname(String cname) {
    	   return courserepo.findByCoursename(cname);
       }
}
