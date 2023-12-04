package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ojt.studentmanagmentsb.controller.UserController;
import com.ojt.studentmanagmentsb.dto.UserDto;
import com.ojt.studentmanagmentsb.service.EmailService;
import com.ojt.studentmanagmentsb.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestUserController {
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	UserService userservice;
	
	@Mock
	EmailService emailservice;
	
	@InjectMocks
	UserController usercontroller;

	@Test
	public void testRegisterUserPost() throws Exception {
	    UserDto userDto = new UserDto();
	    userDto.setUsername("yuri");
	    userDto.setPassword("123");
	    
	   
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-user")
	            .flashAttr("userdto", userDto)
	            .param("email", "test@example.com");

	    mockMvc.perform(requestBuilder)
	            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	            .andExpect(MockMvcResultMatchers.redirectedUrl("/register-user"))
	            .andExpect(MockMvcResultMatchers.flash().attributeExists("userRegisterSuccess"));
	    
//	    String expectedToEmail = "test@example.com";
//	    String expectedSubject = "Email Verification";
//	    String expectedBody = "Dear " + userDto.getUsername() + "\n\nPlease click the link to verify your email "
//	            + "<a href='http://localhost:8080/verify-email/your-email-token'>Verify Email</a>";
//
//	    Mockito.verify(emailservice, Mockito.times(1)).sendEmail(expectedToEmail, expectedSubject, expectedBody);
	}

	   
	}



