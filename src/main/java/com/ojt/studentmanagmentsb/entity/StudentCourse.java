package com.ojt.studentmanagmentsb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_course")
public class StudentCourse {
		@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	
	
		@ManyToOne(fetch = FetchType.EAGER)
		   @JoinColumn(name="student_id",referencedColumnName="id")
		   private Student student;
	
		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="course_id",referencedColumnName="id")
		private Course course;
		

		public StudentCourse(int id, Student student, Course course) {
			super();
			this.id = id;
			this.student = student;
			this.course = course;
		}
		
		
		
		public StudentCourse() {
			super();
		}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public Course getCourse() {
			return course;
		}

		public void setCourse(Course course) {
			this.course = course;
		}
		
		
}
