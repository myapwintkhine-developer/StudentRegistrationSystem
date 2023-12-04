package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
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

import com.ojt.studentmanagmentsb.controller.LogInOutController;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.UserRepository;
import com.ojt.studentmanagmentsb.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestLoginoutController {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userservice;
    
    @Mock
    private UserRepository userrepo;

    @InjectMocks
    private LogInOutController loginoutController;

    @Test
    public void testLoginPost() throws Exception {
    	String username = "123";
        String password = "password";
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
            .param("username", username)
            .param("password", password);
        
        
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("LGN001"));

    }
    
    @Test
    public void testForgotPasswordPost() throws Exception {
        // Set up test data
        String username = "testuser";
        String email = "testuser@gmail.com";

        // Create a mock User object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        // Set other necessary properties for the test

        // Mock the behavior of userservice.getUserByUsernameAndEmail
      //  Mockito.when(userservice.getUserByUsernameAndEmail(username, email)).thenReturn(user);

        // Invoke the forgotPasswordPost method
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/forgot-password")
                .param("username", username)
                .param("email", email);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("USR008"));
           //     .andExpect(MockMvcResultMatchers.model().attributeExists("resetCheckMail"));

        

    }

}
