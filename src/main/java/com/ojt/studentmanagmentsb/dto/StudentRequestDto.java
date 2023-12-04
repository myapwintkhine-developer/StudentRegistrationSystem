package com.ojt.studentmanagmentsb.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDto {
	private int id,student_id, user_id;
	
	@NotBlank(message="Student name can't be blank.")
	private String student_name;
	
	@NotBlank(message="DOB can't be blank.")
	private String dob;
	
	@NotBlank(message="Gender can't be blank.")
	private String gender;
	
	@NotBlank(message="Phone can't be blank.")
	private String phone;
	
	@NotBlank(message="Education can't be blank.")
	private String education;
	
	private String photo;
	private String username;
	private String requestDate;
	private String user_message;
	private String admin_reply;
	private String action;
	private String status;
	
	private String[]course;
	
	@NotNull (message="Please insert an image file.")
	private MultipartFile imageFile;
	
	public StudentRequestDto() {
		super();
	}
	

	public StudentRequestDto(@NotBlank(message = "Student name can't be blank.") String student_name,
			@NotBlank(message = "DOB can't be blank.") String dob,
			@NotBlank(message = "Gender can't be blank.") String gender,
			@NotBlank(message = "Phone can't be blank.") String phone,
			@NotBlank(message = "Education can't be blank.") String education, String user_message, String admin_reply,
			String[] course, @NotNull(message = "Please insert an image file.") MultipartFile imageFile) {
		super();
		this.student_name = student_name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.user_message = user_message;
		this.admin_reply = admin_reply;
		this.course = course;
		this.imageFile = imageFile;
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
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
	public String getUser_message() {
		return user_message;
	}
	public void setUser_message(String user_message) {
		this.user_message = user_message;
	}
	public String getAdmin_reply() {
		return admin_reply;
	}
	public void setAdmin_reply(String admin_reply) {
		this.admin_reply = admin_reply;
	}
	public String[] getCourse() {
		return course;
	}
	public void setCourse(String[] course) {
		this.course = course;
	}
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
