package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.StudentRepository;
import com.ojt.studentmanagmentsb.service.StudentService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestStudentService {
	
	@Mock
	private StudentRepository studentrepo;
	
	@InjectMocks
	private StudentService studentservice;
	
	@Test
	public void testGetAllStudents() {
		
		    // Create users
		    User u1 = new User(1, "mya", "mya@gmail.com", "06-22-2023", "eouadfjdlfjadlfd-123", "USER", "ACTIVE", true, "3942343-dadfjdoru-234397439", "2023-06-30T10:39:52.210591400");
		    User u2 = new User(2, "aye", "aye@gmail.com", "06-22-2023", "eouadfjdlfjadlfd-123", "USER", "ACTIVE", true, "3942343-dadfjdoru-234397439", "2023-06-30T10:39:52.210591400");

		    // Create courses
		    Course c1 = new Course(1, "Java", "VALID");
		    Course c2 = new Course(2, "PHP", "VALID");
		    Course c3 = new Course(3, "Android", "VALID");
		    Course c4 = new Course(4, "Javascript", "VALID");

		    // Create students
		    Student s1 = new Student();
		    s1.setId(1);
		    s1.setStudentname("mya mya");
		    s1.setDob("2015-10-11");
		    s1.setPhone("0939745934");
		    s1.setGender("FEMALE");
		    s1.setRegisterDate("02-22-2023");
		    s1.setPhoto("239473-dwrue-2343243-image");
		    s1.setEducation("Bachelor in Computer Science");
		    s1.setUser(u2);

		    Student s2 = new Student();
		    s2.setId(2);
		    s2.setStudentname("aye aye");
		    s2.setDob("2015-10-11");
		    s2.setPhone("0939745934");
		    s2.setGender("FEMALE");
		    s2.setRegisterDate("02-22-2023");
		    s2.setPhoto("239473-dwrue-2343243-image");
		    s2.setEducation("Bachelor in Computer Science");
		    s2.setUser(u1);

		    Student s3 = new Student();
		    s3.setId(3);
		    s3.setStudentname("min min");
		    s3.setDob("2015-10-11");
		    s3.setPhone("0939745934");
		    s3.setGender("MALE");
		    s3.setRegisterDate("02-22-2023");
		    s3.setPhoto("239473-dwrue-2343243-image");
		    s3.setEducation("Bachelor in Computer Science");
		    s3.setUser(u2);

		    // Create student courses
		    List<StudentCourse> s1scList = new ArrayList<>();
		    s1scList.add(new StudentCourse(1, s1, c1));
		    s1scList.add(new StudentCourse(2, s1, c2));
		    s1scList.add(new StudentCourse(3, s1, c4));
		    s1.setStudentcourse(s1scList);

		    List<StudentCourse> s2scList = new ArrayList<>();
		    s2scList.add(new StudentCourse(3, s2, c1));
		    s2scList.add(new StudentCourse(4, s2, c2));
		    s2.setStudentcourse(s2scList);
		    
		    List<StudentCourse> s3scList = new ArrayList<>();
		    s3scList.add(new StudentCourse(5, s3, c1));
		    s3scList.add(new StudentCourse(6, s3, c4));
		    s3.setStudentcourse(s3scList);
		    
		    
		    List<Student> expectedStudents=new ArrayList<Student>();
		    expectedStudents.add(s1);
		    expectedStudents.add(s2);
		    expectedStudents.add(s3);
		   

		
		when(studentrepo.findAll()).thenReturn(expectedStudents);
		
		List<Student> actualStudents=studentservice.getAllStudents();
		
		assertEquals(expectedStudents,actualStudents);
		
		
	}
	
	
	@Test
	public void testSearchStudentByIdNameCourse() {
		User u1 = new User(1, "mya", "mya@gmail.com", "06-22-2023", "eouadfjdlfjadlfd-123", "USER", "ACTIVE", true, "3942343-dadfjdoru-234397439", "2023-06-30T10:39:52.210591400");
		
		Course c1 = new Course(1, "Java", "VALID");
	    Course c2 = new Course(2, "PHP", "VALID");
	    Course c3 = new Course(3, "Android", "VALID");
	    Course c4 = new Course(4, "Javascript", "VALID");
	    
	    Student s1 = new Student();
	    s1.setId(1);
	    s1.setStudentname("mya mya");
	    s1.setDob("2015-10-11");
	    s1.setPhone("0939745934");
	    s1.setGender("FEMALE");
	    s1.setRegisterDate("02-22-2023");
	    s1.setPhoto("239473-dwrue-2343243-image");
	    s1.setEducation("Bachelor in Computer Science");
	    s1.setUser(u1);
	    
	    List<StudentCourse> s1scList = new ArrayList<>();
	    s1scList.add(new StudentCourse(1, s1, c1));
	    s1scList.add(new StudentCourse(2, s1, c2));
	    s1scList.add(new StudentCourse(3, s1, c4));
	    s1.setStudentcourse(s1scList);
	    
	    List<Student> expectedList=new ArrayList<Student>();
	    expectedList.add(s1);
	    
	    when(studentrepo.findStudentByStudentidAndNameAndCoursename(1,"m","j")).thenReturn(expectedList);
	    List<Student> actualList=studentservice.searchStudentByIdSnameCname(1,"m","j");
	    
	    verify(studentrepo,times(1)).findStudentByStudentidAndNameAndCoursename(1,"m","j");
	    assertEquals(expectedList,actualList);
	    
	}
	

}
