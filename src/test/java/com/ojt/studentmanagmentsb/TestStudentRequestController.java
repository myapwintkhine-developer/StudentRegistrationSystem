package com.ojt.studentmanagmentsb;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ojt.studentmanagmentsb.controller.StudentRequestController;
import com.ojt.studentmanagmentsb.dto.StudentRequestDto;
import com.ojt.studentmanagmentsb.entity.*;
import com.ojt.studentmanagmentsb.service.CourseService;
import com.ojt.studentmanagmentsb.service.StudentRequestService;
import com.ojt.studentmanagmentsb.service.UserService;

import jakarta.servlet.http.HttpSession;


public class TestStudentRequestController {
	
	private MockMvc mockMvc;

    @Mock
    private StudentRequestService stureqservice;

    @Mock
    private CourseService courseservice;

    @Mock
    private UserService userService;

    @InjectMocks
    private StudentRequestController stureqController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stureqController).build();
    }


	@Test
	public void testUserRegisterStudent() {
		User loginUser=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
		String []course= {"java","php"};
		MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test".getBytes());
		StudentRequestDto stureqdto=new StudentRequestDto("Mya Mya","2015-22-10","FEMALE","092493434","Diploma in IT",null,null,course,imageFile);
		
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("loginUser")).thenReturn(loginUser);
		
		when(stureqservice.requestStudent((StudentRequest) any(StudentRequest.class))).thenReturn(1);
        when(courseservice.getCourseByCname(anyString())).thenReturn(new Course());
        when(stureqservice.getSreqById(anyInt())).thenReturn(new StudentRequest());
        when(userService.getUserById(anyInt())).thenReturn(new User());
        when(courseservice.getAllCourses()).thenReturn(new ArrayList<>());
	}

}
