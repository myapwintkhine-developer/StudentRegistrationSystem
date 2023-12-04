package com.ojt.studentmanagmentsb.dto;

import java.util.List;

import com.ojt.studentmanagmentsb.entity.StudentCourse;

import jakarta.validation.constraints.NotBlank;

public class StudentDto {
	private int id, user_id;
	
	@NotBlank(message = "Student Name can't be blank.")
	private String student_name;
	
	@NotBlank(message = "Dob can't be blank.")
	private String dob;
	
	@NotBlank(message = "Gender can't be blank.")
	private String gender;
	
	@NotBlank(message = "Phone can't be blank.")
	private String phone;
	
	@NotBlank(message = "Education can't be blank.")
	private String education;
	
	private String photo,registerDate,username;
	
	private List<StudentCourse> studentcourse;
	public StudentDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
	public List<StudentCourse> getStudentcourse() {
		return studentcourse;
	}
	public void setStudentcourse(List<StudentCourse> studentcourse) {
		this.studentcourse = studentcourse;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
