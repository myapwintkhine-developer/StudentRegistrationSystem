package com.ojt.studentmanagmentsb.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_request")
public class StudentRequest {
	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	@Column(name="student_id")
    private int studentid;
    
    @Column(name="student_name",length=50,nullable=false)
    private String studentname;
    
    @Column(length=20,nullable=false)
    private String dob;
    
    @Column(length=10,nullable=false)
    private String gender;
    
    @Column(length=20,nullable=false)
    private String phone;
    
    @Column(length=200,nullable=false)
    private String education;
    
    @Column(length=500,nullable=false)
    private String photo;
    
    @Column(length=20,nullable=false)
    private String requestDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name="user_id",referencedColumnName="id")
	   private User user;
    
    @Column(length=10,nullable=false)
    private String action;
    
    @Column(length=15,nullable=false)
    private String status;
    
    @Column(length=500,nullable=true)
    private String user_message;
    
    @Column(length=500,nullable=true)
    private String admin_reply;
    
    @OneToMany(mappedBy = "studentRequest",fetch = FetchType.EAGER)
    private List<StudentCourseRequest> studentcourserequest;

	public StudentRequest() {
		super();
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

	
	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
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

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<StudentCourseRequest> getStudentcourserequest() {
		return studentcourserequest;
	}

	public void setStudentcourserequest(List<StudentCourseRequest> studentcourserequest) {
		this.studentcourserequest = studentcourserequest;
	}
    
    
}
