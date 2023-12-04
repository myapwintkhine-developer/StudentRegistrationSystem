package com.ojt.studentmanagmentsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(length=50,nullable=false)
	private String username;
	
	
	@Column(length=50,nullable=false)
	private String email;
	
	
	@Column(name="registerDate",length=15,nullable=false)
	private String registerDate;
	
	
	@Column(length=200,nullable=false)
	private String password;
	
	
	@Column(length=10,nullable=false)
	private String role;
	
	
	@Column(length=20,nullable=false)
	private String status;
	
	@Column(nullable=false)
	private boolean emailverified;
	
	@Column(length=100,nullable=true)
	private String emailtoken;
	
	private String email_expiration_date;
	
	
	
	public User() {
		super();
	}
	
	
	
	public User(int id, String username, String email, String registerDate, String password, String role, String status,
			boolean emailverified, String emailtoken, String email_expiration_date) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.registerDate = registerDate;
		this.password = password;
		this.role = role;
		this.status = status;
		this.emailverified = emailverified;
		this.emailtoken = emailtoken;
		this.email_expiration_date = email_expiration_date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isEmailverified() {
		return emailverified;
	}
	public void setEmailverified(boolean emailverified) {
		this.emailverified = emailverified;
	}
	public String getEmailtoken() {
		return emailtoken;
	}
	public void setEmailtoken(String emailtoken) {
		this.emailtoken = emailtoken;
	}
	public String getEmail_expiration_date() {
		return email_expiration_date;
	}
	public void setEmail_expiration_date(String email_expiration_date) {
		this.email_expiration_date = email_expiration_date;
	}
	
	}

