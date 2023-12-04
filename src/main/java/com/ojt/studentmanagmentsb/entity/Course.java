package com.ojt.studentmanagmentsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "course")

public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Course name can't be blank.")
	@Column(name = "course_name",length=255,nullable=false)
	private String coursename;
	
	@Column(name = "status",length=10,nullable=false)
	private String status;
	

	public Course() {
		super();
	}
	
	

	public Course(int id, @NotBlank(message = "Course name can't be blank.") String coursename, String status) {
		super();
		this.id = id;
		this.coursename = coursename;
		this.status = status;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
