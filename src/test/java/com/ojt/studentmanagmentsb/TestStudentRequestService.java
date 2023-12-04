package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
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
import com.ojt.studentmanagmentsb.entity.StudentCourseRequest;
import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.StudentRequestRepository;
import com.ojt.studentmanagmentsb.service.StudentRequestService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestStudentRequestService {
	
	@Mock
	private StudentRequestRepository stureqrepo;
	
	@InjectMocks
	private StudentRequestService stureqservice;

	@Test
	public void testRequestStudent() {
		User user=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
		
		Course c1=new Course(1,"Java","VALID");
		Course c2=new Course(2,"PHP","VALID");
		Course c3=new Course(3,"Android","VALID");
		
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(c1);
		courseList.add(c2);
		courseList.add(c3);
		
		StudentRequest stureq=new StudentRequest();
		stureq.setStudentname("Aye Aye");
		stureq.setDob("2015-10-22");
		stureq.setGender("FEMALE");
		stureq.setPhone("093484723");
		stureq.setPhoto("0348234-adadiufe-23432048-image");
		stureq.setRequestDate("6-4-2023");
		stureq.setEducation("Diploma in IT");
		stureq.setUser(user);
		stureq.setAction("INSERT");
		stureq.setStatus("PENDING");
		stureq.setStudentid(0);
		stureq.setUser_message(null);
		stureq.setAdmin_reply(null);
		
		List<StudentCourseRequest> stucoureqList=new ArrayList<StudentCourseRequest>();
		
		for(Course c:courseList) {
			stucoureqList.add(new StudentCourseRequest(0,c,stureq));
		}
		
		stureq.setStudentcourserequest(stucoureqList);
		when(stureqrepo.save(stureq)).thenReturn(stureq);
		
		int result=stureqservice.requestStudent(stureq);
		
		verify(stureqrepo, times(1)).save(stureq);
		assertEquals(stureq.getId(), result);
		}
	
	
	
	@Test
	public void testSearchByAllForHistory() {
		User user=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
		
		Course c1=new Course(1,"Java","VALID");
		Course c2=new Course(2,"PHP","VALID");
		Course c3=new Course(3,"Android","VALID");
		
		//for request 1
		
		StudentRequest stureq1=new StudentRequest();
		stureq1.setId(1);
		stureq1.setStudentname("Aye Aye");
		stureq1.setDob("2015-10-22");
		stureq1.setGender("FEMALE");
		stureq1.setPhone("093484723");
		stureq1.setPhoto("0348234-adadiufe-23432048-image");
		stureq1.setRequestDate("6-4-2023");
		stureq1.setEducation("Diploma in IT");
		stureq1.setUser(user);
		stureq1.setAction("INSERT");
		stureq1.setStatus("APPROVED");
		stureq1.setStudentid(5);
		stureq1.setUser_message(null);
		stureq1.setAdmin_reply(null);
		
		List<StudentCourseRequest> stucoureqList1=new ArrayList<StudentCourseRequest>();
		stucoureqList1.add(new StudentCourseRequest(1,5,c1,stureq1));
		stucoureqList1.add(new StudentCourseRequest(2,5,c2,stureq1));
		stucoureqList1.add(new StudentCourseRequest(3,5,c3,stureq1));
		stureq1.setStudentcourserequest(stucoureqList1);
		
		//for request 2
		
		StudentRequest stureq2=new StudentRequest();
		stureq2.setId(2);
		stureq2.setStudentname("Aye Aye");
		stureq2.setDob("2015-10-22");
		stureq2.setGender("FEMALE");
		stureq2.setPhone("093484723");
		stureq2.setPhoto("0348234-adadiufe-23432048-image");
		stureq2.setRequestDate("6-4-2023");
		stureq2.setEducation("Diploma in IT");
		stureq2.setUser(user);
		stureq2.setAction("DELETE");
		stureq2.setStatus("REJECTED");
		stureq2.setStudentid(5);
		stureq2.setUser_message(null);
		stureq2.setAdmin_reply(null);
		
		List<StudentCourseRequest> stucoureqList2=new ArrayList<StudentCourseRequest>();
		stucoureqList2.add(new StudentCourseRequest(4,5,c1,stureq2));
		stucoureqList2.add(new StudentCourseRequest(5,5,c2,stureq2));
		
		stureq2.setStudentcourserequest(stucoureqList2);
		
		//for request 3
		StudentRequest stureq3=new StudentRequest();
		stureq3.setId(3);
		stureq3.setStudentname("Aye Aye");
		stureq3.setDob("2015-10-22");
		stureq3.setGender("FEMALE");
		stureq3.setPhone("093484723");
		stureq3.setPhoto("0348234-adadiufe-23432048-image");
		stureq3.setRequestDate("6-4-2023");
		stureq3.setEducation("Diploma in IT");
		stureq3.setUser(user);
		stureq3.setAction("DELETE");
		stureq3.setStatus("PENDING");
		stureq3.setStudentid(5);
		stureq3.setUser_message(null);
		stureq3.setAdmin_reply(null);
		
		List<StudentCourseRequest> stucoureqList3=new ArrayList<StudentCourseRequest>();
		stucoureqList3.add(new StudentCourseRequest(6,5,c1,stureq3));
		stucoureqList3.add(new StudentCourseRequest(7,5,c2,stureq3));
		
		stureq3.setStudentcourserequest(stucoureqList3);
		
		List<StudentRequest> expectedList=new ArrayList<StudentRequest>();
		expectedList.add(stureq2);
		expectedList.add(stureq3);
		
		when(stureqrepo.findRequestByIdNameActionStatusCourse(user,5,"a","d","e","j")).thenReturn(expectedList);
		List<StudentRequest> actualList=stureqservice.searchByAll(user,5,"a","d","e","j");
		
		verify(stureqrepo, times(1)).findRequestByIdNameActionStatusCourse(user,5,"a","d","e","j");
		assertEquals(expectedList, actualList);
	}

}
