package com.ojt.studentmanagmentsb;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ojt.studentmanagmentsb.controller.AdminStudentController;
import com.ojt.studentmanagmentsb.dto.StudentRequestDto;
import com.ojt.studentmanagmentsb.service.StudentCourseService;
import com.ojt.studentmanagmentsb.service.StudentRequestService;
import com.ojt.studentmanagmentsb.service.StudentService;
import com.ojt.studentmanagmentsb.service.UserService;
import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentRequest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TestAdminStudentController {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private AdminStudentController adminstucontroller;
	
	@Mock
	private StudentService studentservice;
	
	@Mock
	private StudentRequestService stureqservice;
	
	@Mock
	private StudentCourseService stucouservice;
	
	@Mock
	private UserService userservice;
	
	

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminstucontroller).build();
    }

	@Test
	void testAdminApprove() throws Exception {
		StudentRequest stureq=new StudentRequest();
		int studentid=0;
		String buttonaction="approve";
		StudentRequestDto stureqdto = new StudentRequestDto();
		stureqdto.setStudent_id(studentid);
		stureqdto.setAction("INSERT");
        stureqdto.setStatus("PENDING");
        
        String [] course_list= {"java","php"};
        
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin-confirm")
                .flashAttr("stureqdto", stureqdto)
                .param("buttonaction", buttonaction)
                .param("course", course_list);
        
        mockMvc.perform(requestBuilder)
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/admin-view-requests"));
        
        verify(studentservice, times(1)).createStudent(any(Student.class));
        verify(studentservice, times(1)).deleteStudentById(anyInt());
        verify(redirectAttributes, times(1)).addFlashAttribute("adminApproveSuccess", anyString());
        verify(redirectAttributes, never()).addFlashAttribute("adminRejectSuccess", anyString());
        verify(model, never()).addAttribute(anyString(), any());
	}

}
