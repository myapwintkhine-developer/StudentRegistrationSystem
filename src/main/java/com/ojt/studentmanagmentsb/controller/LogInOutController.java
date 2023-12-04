package com.ojt.studentmanagmentsb.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojt.studentmanagmentsb.entity.Reset;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.other.TokenGenerator;
import com.ojt.studentmanagmentsb.service.EmailService;
import com.ojt.studentmanagmentsb.service.ResetService;
import com.ojt.studentmanagmentsb.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class LogInOutController {
	@Autowired UserService userservice;
	@Autowired TokenGenerator tokengenerator;
	@Autowired ResetService resetservice;
	@Autowired EmailService emailservice;
	
	@GetMapping("/login")
	public String loginGet() {
		return "LGN001";
	}
	
	@PostMapping("/login")
	public String loginPost(@RequestParam("username")String username, @RequestParam("password")String password,HttpServletResponse response,HttpSession session,Model model) {
		model.getAttribute("verifyError");
		User loginUser=userservice.getUserByUsername(username);
		if(loginUser != null && loginUser.getUsername().equals(username) && BCrypt.checkpw(password, loginUser.getPassword()) && loginUser.getStatus().equals("ACTIVE")) {
			session.setAttribute("loginUser", loginUser);
		return "MNU001";
	}else if(loginUser != null && loginUser.getStatus().equals("INACTIVE")){
		model.addAttribute("userInactiveError","Sorry! Either you haven't verified your email or your account has been deactivated.");
		return "LGN001";
	}
		else {
		model.addAttribute("loginError","Username or password incorrect! Please try again!");
		return "LGN001";
	}
	

	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		User logoutUser=(User) session.getAttribute("loginUser");
		if(logoutUser != null) {
			session.removeAttribute("loginUser");
			
		}
		return "redirect:/login";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "USR008";
	}
	
	@PostMapping("/forgot-password")
	public String forgotPasswordPost(@RequestParam("username")String username,@RequestParam("email")String email, Model model) {
		User user=userservice.getUserByUsernameAndEmail(username, email);
		if(user != null) {
			if(user.getStatus().equals("INACTIVE")) {
				model.addAttribute("resetInactiveError","Sorry! Inactive account. Your account has been deactivated or you haven't verified your email.");
			}else {
			Reset reset=new Reset();
			reset.setResettoken(tokengenerator.generateToken());
			reset.setReset_expiration_date(LocalDateTime.now().plusHours(24).toString());
			reset.setStatus("PENDING");
			reset.setUser(user);
			resetservice.createReset(reset);
			
			String toEmail=user.getEmail();
			String subject="Password reset";
			String body="Dear "+user.getUsername()+"\n\nPlease click the lick to reset your password "+"<a href='http://localhost:8080/reset-password/"+reset.getResettoken()+"'>Reset Password</a>";
			emailservice.sendEmail(toEmail, subject, body);
			
			model.addAttribute("resetCheckMail", "Please check your email to reset your password");
			}
			
		}else {
			model.addAttribute("emailError", "Sorry! Incorrect username or email!");
		}
		return "USR008";
	}
	
	
	@GetMapping("/reset-password/{resettoken}")
	public String resetPassword(@PathVariable("resettoken")String resettoken,Model model) {
		if(resettoken != null) {
			Reset reset=resetservice.getResetByResetToken(resettoken);
			if(reset != null) {
				User user=reset.getUser();
				model.addAttribute("userToReset", user);
				return "USR009";
			}
			else {
				model.addAttribute("resetNullError","Something went wrong! Please enter your email again.");
				return "USR008";
			}
			
		}
		else {
			model.addAttribute("tokenNullError", "Something went wrong! Please click the link in your mail again.");
			return "USR008";
		}
		
	}
	
	@PostMapping("/reset-password")
	public String resetPasswordPost(@ModelAttribute("userToReset")User userToReset,@RequestParam("password")String password,HttpSession session) {
		
		User user=userservice.getUserById(userToReset.getId());
		String hashedPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		
		user.setPassword(hashedPassword);
		userservice.createUser(user);
		session.setAttribute("loginUser", user);
		
		List<Reset> retrievedResetList=resetservice.getResetByStatusAndUser("PENDING", user);
		Reset latestReset=new Reset();
		for(Reset rs:retrievedResetList) {
			latestReset=rs;
		}
		latestReset.setStatus("Success");
		resetservice.createReset(latestReset);
		return "MNU001";
		
	}

}
