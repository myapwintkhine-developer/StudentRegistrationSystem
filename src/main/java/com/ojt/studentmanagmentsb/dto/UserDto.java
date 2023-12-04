package com.ojt.studentmanagmentsb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
	private int id;
	
	@NotBlank(message="Username can't be blank.")
	private String username;
	
	@Email(message="Invalid email form")
	@NotBlank(message="Email can't be blank.")
	private String email;
	
	@NotBlank(message="Password can't be blank.")
	private String password;
	private String role;
	public UserDto() {
		super();
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
	
	
}
