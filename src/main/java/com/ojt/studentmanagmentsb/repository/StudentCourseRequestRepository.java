package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.StudentCourseRequest;
import com.ojt.studentmanagmentsb.entity.StudentRequest;


@Repository
public interface StudentCourseRequestRepository extends JpaRepository<StudentCourseRequest, Integer>{
   List<StudentCourseRequest> findByStudentRequest(StudentRequest studentrequest);
}
