package com.ojt.studentmanagmentsb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentView;
import com.ojt.studentmanagmentsb.repository.StudentRepository;
import com.ojt.studentmanagmentsb.repository.StudentViewRepository;



@Service
public class StudentService {
	@Autowired StudentRepository studentrepo;
	@Autowired StudentViewRepository studentviewrepo;
	public Student getStudentById(int id) {
		  Optional<Student> student = studentrepo.findById(id);
		    return student.orElse(null);
	}
	
	public boolean createOrUpdateStudent(Student student) {
		Student savedStudent=studentrepo.save(student);
		return savedStudent != null;
	}
	
	public int createStudent(Student student) {
		Student savedStudent=studentrepo.save(student);
		return savedStudent.getId();
	}
	
	public boolean deleteStudentById(int id) {
		try {
			studentrepo.deleteById(id);
			 return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public List<Student> getAllStudents(){
		return studentrepo.findAll();
	}
	
	public List<Student> searchStudentBySname(String studentname) {
		return studentrepo.findStudentByStudentname(studentname);
	}
	
	public Student searchStudentByIdName(int id,String studentname) {
		return studentrepo.findStudentByIdAndStudentname(id, studentname);
	}
	
	public List<Student> searchStudentByCname(String coursename){
		return studentrepo.findStudentByCoursename(coursename);
	}
	
	public List<Student> searchStudentByIdSnameCname(int id, String studentname,String coursename){
		return studentrepo.findStudentByStudentidAndNameAndCoursename(id, studentname, coursename);
	}
	
	public List<Student> searchStudentByIdCname(int id,String coursename){
		return studentrepo.findStudentByStudentidAndCoursename(id, coursename);
	}
	
	public List<Student> searchStudentBySnameCname(String studentname,String coursename){
		return studentrepo.findStudentByStudentnameAndCoursename(studentname, coursename);
	}
	
	public List<StudentView> getStudentForReport(){
		return studentviewrepo.findAll();
	}
	
	
}


