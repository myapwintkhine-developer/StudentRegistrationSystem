package com.ojt.studentmanagmentsb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.service.CourseService;

import jakarta.servlet.http.HttpSession;





@Controller
public class CourseController {
		@Autowired CourseService courseservice;
		
		@GetMapping("/create-course")
		public String createCourseGet(HttpSession session,Model model) {
			User loginUser=(User) session.getAttribute("loginUser");
			if(loginUser != null) {
				model.addAttribute("course", new Course());
			return "BUD003";}
			else {
				return "redirect:/login";
			}
		}
		
		@PostMapping("/create-course")
		public String createCoursePost(@ModelAttribute("course")@Validated Course course,ModelMap model,BindingResult bindingResult ) {
			if (bindingResult.hasErrors()) {
			 	model.addAttribute("course", course);
		        return "BUD003";
		    }
			
			course.setStatus("VALID");
			boolean createCourse=courseservice.createCourse(course);
			
			if(createCourse) {
				model.addAttribute("createCourseSuccess", "Course creation success");
				return "redirect:/create-course";
				
			}
			else {
				model.addAttribute("createCourseError", "Course creation failed. Please try again.");
				return "BUD003";
				
			}
			
		}
		
	
}
