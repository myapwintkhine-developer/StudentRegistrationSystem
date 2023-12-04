package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.ojt.studentmanagmentsb.controller.StudentController;
import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.service.CourseService;
import com.ojt.studentmanagmentsb.service.StudentRequestService;
import com.ojt.studentmanagmentsb.service.StudentService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)

class TestStudentController {

	

	    @InjectMocks
	    private StudentController studentcontroller;
	    
	    @Mock
	    private StudentService studentservice;
	    
	    @Mock
	    private CourseService courseservice;
	    
	    @Mock
	    private StudentRequestService stureqservice;

	    @Test
	    public void testViewOneStudent() throws Exception {
	        int id = 1; 
	        Course c1 = new Course(1, "Java", "VALID");
		    Course c2 = new Course(2, "PHP", "VALID");
		    Course c3 = new Course(3, "Android", "VALID");
		    Course c4 = new Course(4, "Javascript", "VALID");
		    
		    Student student = new Student();
		    
		    List<StudentCourse> scList=new ArrayList<StudentCourse>();
	        scList.add(new StudentCourse(1,student,c1));
	        scList.add(new StudentCourse(2,student,c2));
	        scList.add(new StudentCourse(3,student,c3));
	        
	        student.setStudentcourse(scList);
	        
	        User loginUser=new User(1,"aye","aye@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");

	        HttpSession session = new MockHttpSession();
	        session.setAttribute("loginUser", loginUser);
	        Model model = mock(Model.class);
	        when(studentservice.getStudentById(eq(id))).thenReturn(student);

	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentcontroller).build();

	        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/view-one-student/{id}", id)
	                .session((MockHttpSession) session);

	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	        int expectedStatus = 200; 
	        assertEquals(expectedStatus, result.getResponse().getStatus());

	       
	    }
	    
	    @Test
	    public void testSearchStudentByAll() {
	    	Model model = mock(Model.class);
	    	String sid = "1";
	        String studentname = "mya mya";
	        String coursename = "java";
	        
	        List<Student> searchStudentList = new ArrayList<>();
	        Student student = new Student();
	        student.setId(1);
	        student.setStudentname("mya mya");
	        searchStudentList.add(student);
	        
	        when(studentservice.searchStudentByIdSnameCname(1, "mya mya", "java")).thenReturn(searchStudentList);
	        String result = studentcontroller.searchStudent(sid, studentname, coursename, model);
	        
	        assertEquals("STU004", result);
	        verify(model).addAttribute("searchStudentList", searchStudentList);
	        verify(model, never()).addAttribute("searchError", "Student not found!");

	        verify(studentservice).searchStudentByIdSnameCname(1, "mya mya", "java");
	    }
	    
	    @Test
	    public void testSearchStudentBySnameCname() {
	    	Course c1 = new Course(1, "Java", "VALID");
		    Course c2 = new Course(2, "PHP", "VALID");
		    Course c3 = new Course(3, "Android", "VALID");
		    Course c4 = new Course(4, "Javascript", "VALID");
	    	
	    	Model model = mock(Model.class);
	    	String sid="";
	    	String studentname="m";
	    	String coursename="j";
	    	
	    	
	    	List<Student> searchStudentList = new ArrayList<>();
	    	Student student1=new Student();
	    	student1.setStudentname("mya mya");
	    	List<StudentCourse> scList=new ArrayList<StudentCourse>();
	    	scList.add(new StudentCourse(1,student1,c1)); 
	    	scList.add(new StudentCourse(2,student1,c2));
	    	student1.setStudentcourse(scList);
	    	
	    	Student student2=new Student();
	    	student2.setStudentname("moe moe");
	    	student2.setStudentcourse(scList);
	    	
	    	searchStudentList.add(student1);
	    	searchStudentList.add(student2);
	    	
	    	when(studentservice.searchStudentBySnameCname("m","j")).thenReturn(searchStudentList);
	        String result = studentcontroller.searchStudent(sid,studentname, coursename, model);
	        
	        assertEquals("STU004", result);
	        verify(model).addAttribute("searchStudentList", searchStudentList);
	        verify(model, never()).addAttribute("searchError", "Student not found!");

	        verify(studentservice).searchStudentBySnameCname("m", "j");
	    }
	}


