package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.repository.CourseRepository;
import com.ojt.studentmanagmentsb.service.CourseService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestCourseService {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testCreateCourse() {
        Course sampleCourse = new Course();
        sampleCourse.setCoursename("java");
        sampleCourse.setStatus("VALID");

        when(courseRepository.save(sampleCourse)).thenReturn(sampleCourse);

        boolean result = courseService.createCourse(sampleCourse);

        assertTrue(result);
    }
}



