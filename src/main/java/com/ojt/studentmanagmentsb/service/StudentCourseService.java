package com.ojt.studentmanagmentsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.repository.StudentCourseRepository;



@Service
public class StudentCourseService {
	@Autowired StudentCourseRepository stucourepo;
	
	
	public boolean createStudentCourse(StudentCourse studentcourse) {
		StudentCourse savedStuCou=stucourepo.save(studentcourse);
		return savedStuCou != null;
	}
	
	public List<StudentCourse> getSCByStudent(Student student) {
		return stucourepo.findByStudent(student);
	}
	
	public boolean deleteSCById(int id) {
		try {
			stucourepo.deleteById(id);
			 return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
}
