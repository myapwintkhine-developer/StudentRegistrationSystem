package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.repository.StudentCourseRepository;
import com.ojt.studentmanagmentsb.service.StudentCourseService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TestStudentCourseService {
	
	@Mock
	private StudentCourseRepository stucourepo;
	
	@InjectMocks
	private StudentCourseService stucouservice;

	@Test
	void testDeleteStudentCourse() {
		stucouservice.deleteSCById(1);
		verify(stucourepo,times(1)).deleteById(1);
	}
}
