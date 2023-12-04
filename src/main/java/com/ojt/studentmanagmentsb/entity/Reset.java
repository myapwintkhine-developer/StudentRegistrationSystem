package com.ojt.studentmanagmentsb.entity;

import java.time.LocalDateTime;

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
@Table(name = "password_reset")
public class Reset {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	   @JoinColumn(name="user_id",referencedColumnName="id")
	   private User user;
	
	@Column(name="reset_token",length=100,nullable=true)
	private String resettoken;
	
	private String reset_expiration_date;
	
	@Column(name = "status",length=15,nullable=false)
	private String status;

	public Reset() {
		super();
	}
	

	public Reset(int id, User user, String resettoken, String reset_expiration_date, String status) {
		super();
		this.id = id;
		this.user = user;
		this.resettoken = resettoken;
		this.reset_expiration_date = reset_expiration_date;
		this.status = status;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public String getResettoken() {
		return resettoken;
	}

	public void setResettoken(String resettoken) {
		this.resettoken = resettoken;
	}

	

	public String getReset_expiration_date() {
		return reset_expiration_date;
	}

	public void setReset_expiration_date(String reset_expiration_date) {
		this.reset_expiration_date = reset_expiration_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
