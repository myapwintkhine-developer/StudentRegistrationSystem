package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import com.ojt.studentmanagmentsb.controller.CourseController;
import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.CourseRepository;
import com.ojt.studentmanagmentsb.service.CourseService;
import org.mockito.junit.jupiter.MockitoExtension;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestCourseController {
	@InjectMocks
	private CourseController coursecontroller;
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseservice;
    
    @MockBean
    private CourseRepository courserepo;
    
    @Mock
    private ModelMap modelMap;

    @Mock
    private BindingResult bindingResult;
    
    @Mock
    private MockHttpSession mockhttpsession;

	
	
    @Test
    public void testCreateCourseGet() throws Exception {
    	
    	User loginUser=new User();
    	loginUser.setId(1);
    	loginUser.setUsername("mya mya");
    	loginUser.setEmail("mya@gmail.com");
    	loginUser.setRegisterDate("06-22-2023");
    	loginUser.setRole("USER");
    	loginUser.setStatus("ACTIVE");
    	loginUser.setPassword("322374392-32473294");
    	loginUser.setEmailtoken("3942343-dadfjdoru-234397439");
    	loginUser.setEmailverified(true);
    	loginUser.setEmail_expiration_date("2023-06-30T10:39:52.210591400");
    	mockhttpsession.setAttribute("loginUser",loginUser );
    	Mockito.when(mockhttpsession.getAttribute("org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS"))
        .thenReturn(null);

        Mockito.when(mockhttpsession.getAttribute("loginUser"))
        .thenReturn(loginUser);

    	this.mockMvc.perform(get("/create-course").session(mockhttpsession))
         .andExpect(status().isOk())
         .andExpect(view().name("BUD003"));
    }

    @Test
    public void testCreateCoursePostSuccess() {
        Course course=new Course();
        course.setCoursename("Java");
        //course.setStatus("VALID");
        
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseservice.createCourse(course)).thenReturn(true);
        
        String result = coursecontroller.createCoursePost(course, modelMap, bindingResult);
        assertEquals("redirect:/create-course", result);
        verify(courseservice).createCourse(course);
        verify(modelMap).addAttribute(eq("createCourseSuccess"), any());
        verify(modelMap, never()).addAttribute(eq("createCourseError"), any());
    }
    	
     @Test
     public void testCreateCourseCourseEmpty() {
    	 Course course=new Course();
    	 when(bindingResult.hasErrors()).thenReturn(true);
    	 
    	 String result = coursecontroller.createCoursePost(course, modelMap, bindingResult);
    	 assertEquals("BUD003", result);
    	 verify(modelMap).addAttribute(eq("course"), eq(course));
         verify(modelMap, never()).addAttribute(eq("createCourseSuccess"), any());
         verify(modelMap, never()).addAttribute(eq("createCourseError"), any());
    	 


     }
       

     @Test
     public void testCreateCourseFail() {
    	 Course course=new Course();
    	 course.setCoursename("Java");
    	 when(bindingResult.hasErrors()).thenReturn(false);
    	 
    	 when(courseservice.createCourse(course)).thenReturn(false);
    	 String result = coursecontroller.createCoursePost(course, modelMap, bindingResult);
    	 
    	 assertEquals("BUD003", result);
         verify(courseservice).createCourse(course);
         verify(modelMap).addAttribute(eq("createCourseError"), any());
         verify(modelMap, never()).addAttribute(eq("createCourseSuccess"), any());
     }
    }



