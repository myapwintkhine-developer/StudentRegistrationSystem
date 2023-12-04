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
@Table(name = "student")
public class Student {
		@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
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
	    private String registerDate;
	    
	       @ManyToOne(fetch = FetchType.LAZY)
		   @JoinColumn(name="user_id",referencedColumnName="id")
		   private User user;
	       
	       @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
	       private List<StudentCourse> studentcourse;


		public Student() {
			super();
		}
		

		public Student(int id, String studentname, String dob, String gender, String phone, String education,
				String photo, String registerDate, User user, List<StudentCourse> studentcourse) {
			super();
			this.id = id;
			this.studentname = studentname;
			this.dob = dob;
			this.gender = gender;
			this.phone = phone;
			this.education = education;
			this.photo = photo;
			this.registerDate = registerDate;
			this.user = user;
			this.studentcourse = studentcourse;
		}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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

		public String getRegisterDate() {
			return registerDate;
		}

		public void setRegisterDate(String registerDate) {
			this.registerDate = registerDate;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<StudentCourse> getStudentcourse() {
			return studentcourse;
		}

		public void setStudentcourse(List<StudentCourse> studentcourse) {
			this.studentcourse = studentcourse;
		}
		
		
	       
	       
}
