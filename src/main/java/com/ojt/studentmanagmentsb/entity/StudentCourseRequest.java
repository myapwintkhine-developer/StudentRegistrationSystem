package com.ojt.studentmanagmentsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_course_request")
public class StudentCourseRequest {
		@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@Column(name="student_id")
		private int studentid;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="course_id",referencedColumnName="id")
		private Course course;
		
		@ManyToOne(fetch = FetchType.EAGER)
		   @JoinColumn(name="student_request_id",referencedColumnName="id")
		   private StudentRequest studentRequest;
		
		

		public StudentCourseRequest() {
			super();
		}
		
		

		public StudentCourseRequest(int studentid, Course course, StudentRequest studentRequest) {
			super();
			this.studentid = studentid;
			this.course = course;
			this.studentRequest = studentRequest;
		}
		
		

		public StudentCourseRequest(int id, int studentid, Course course, StudentRequest studentRequest) {
			super();
			this.id = id;
			this.studentid = studentid;
			this.course = course;
			this.studentRequest = studentRequest;
		}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		

		public int getStudentid() {
			return studentid;
		}

		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}

		public Course getCourse() {
			return course;
		}

		public void setCourse(Course course) {
			this.course = course;
		}

		

		public StudentRequest getStudentRequest() {
			return studentRequest;
		}

		public void setStudentRequest(StudentRequest studentRequest) {
			this.studentRequest = studentRequest;
		}
		
		
}
