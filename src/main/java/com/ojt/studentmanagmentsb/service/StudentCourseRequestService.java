package com.ojt.studentmanagmentsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.StudentCourseRequest;
import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.repository.StudentCourseRequestRepository;



@Service
public class StudentCourseRequestService {
	@Autowired StudentCourseRequestRepository stucoursereqrepo;
	
	public boolean requestStudentCourse(StudentCourseRequest stucoursereq) {
		StudentCourseRequest savedstucoursereq=stucoursereqrepo.save(stucoursereq);
		return savedstucoursereq != null;
	}
	
	public List<StudentCourseRequest> getSCreqBySreq(StudentRequest stureq){
		return stucoursereqrepo.findByStudentRequest(stureq);
	}
	
}
