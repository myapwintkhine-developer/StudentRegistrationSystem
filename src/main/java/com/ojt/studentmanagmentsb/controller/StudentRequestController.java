package com.ojt.studentmanagmentsb.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ojt.studentmanagmentsb.dto.StudentDto;
import com.ojt.studentmanagmentsb.dto.StudentRequestDto;
import com.ojt.studentmanagmentsb.entity.Course;
import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.entity.StudentCourseRequest;
import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.service.CourseService;
import com.ojt.studentmanagmentsb.service.StudentCourseRequestService;
import com.ojt.studentmanagmentsb.service.StudentCourseService;
import com.ojt.studentmanagmentsb.service.StudentRequestService;
import com.ojt.studentmanagmentsb.service.StudentService;
import com.ojt.studentmanagmentsb.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentRequestController {
	
	@Autowired StudentRequestService studentreqservice;
	@Autowired StudentCourseRequestService stucoursereqservice;
	@Autowired CourseService courseservice;
	@Autowired UserService userservice;
	@Autowired StudentService studentservice;
	@Autowired StudentCourseService stucouservice;

		
	@GetMapping("/user-register-student")
	public ModelAndView requestStudentRegisterGet(Model model) {
		String success=(String) model.asMap().get("StudentRegisterReqSuccess");
		String error=(String) model.asMap().get("StudentRegisterReqError");
		if(success != null) {
			model.addAttribute("StudentRegisterReqSuccess", success);
		}
		if(error != null) {
			model.addAttribute("StudentRegisterReqError", error);
		}
		List<Course> course_list=courseservice.getAllCourses();
		model.addAttribute("course_list",course_list);
		
		return new ModelAndView("STU001","studentReqDto",new StudentRequestDto());
	}
	
	@PostMapping("/user-register-student")
	public String requestStudentRegisterPost(@ModelAttribute("studentReqDto")@Validated StudentRequestDto studentReqDto, HttpSession session,Model model,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
		 	model.addAttribute("studentReqDto", studentReqDto);
	        return "STU001";
	    }
		
		StudentRequest studentreq=new StudentRequest();
		int stureqid=0;
		boolean stucouregisterreq=false;
		
		//take data from form
		studentreq.setStudentname(studentReqDto.getStudent_name());
		studentreq.setDob(studentReqDto.getDob());
		studentreq.setGender(studentReqDto.getGender());
		studentreq.setPhone(studentReqDto.getPhone());
		studentreq.setEducation(studentReqDto.getEducation());
		studentreq.setUser_message(studentReqDto.getUser_message());
		studentreq.setAdmin_reply(studentReqDto.getAdmin_reply());
		
		//for student_id, action and status
				studentreq.setStudentid(0);
				studentreq.setAction("INSERT");
				studentreq.setStatus("PENDING");
		
		//for image
		MultipartFile multipartFile=studentReqDto.getImageFile();
		if(multipartFile !=null && !multipartFile.isEmpty()) {
			String uniqueName=UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
			String fileName="D:\\studentmanagmentsb\\src\\main\\resources\\static\\image\\"+uniqueName;
			try {
				multipartFile.transferTo(new File(fileName));
				studentreq.setPhoto(uniqueName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//for user_id
		User loginUser=(User) session.getAttribute("loginUser");
		int user_id=loginUser.getId();
		User user=userservice.getUserById(user_id);
		studentreq.setUser(user);
		
		
		//for request date
		LocalDate currentDate=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String requestDate = currentDate.format(formatter);
		studentreq.setRequestDate(requestDate);
		
		//insert data in student_request
		stureqid=studentreqservice.requestStudent(studentreq);
		
		//for course
		if(stureqid > 0) {
		
		Course course=new Course();
		
		
		if(studentReqDto.getCourse() != null) {   //loop for each cname
			for(String cname:studentReqDto.getCourse()) {
				StudentCourseRequest stucoursereq=new StudentCourseRequest();
				stucoursereq.setStudentid(studentreq.getStudentid());
				
				course=courseservice.getCourseByCname(cname);
				stucoursereq.setCourse(course);
				
				StudentRequest stureq=studentreqservice.getSreqById(stureqid);
				stucoursereq.setStudentRequest(stureq);
				
				if(stureq != null) {
				stucouregisterreq=stucoursereqservice.requestStudentCourse(stucoursereq);
				}
			}
		}
		}
		
		if(stucouregisterreq) {
			redirectAttributes.addFlashAttribute("StudentRegisterReqSuccess","You have requested student registration successfully! You can register for next student.");
			return "redirect:/user-register-student";
		}
		else {
			redirectAttributes.addFlashAttribute("StudentRegisterReqError","Something went wrong. Please register again.");
			return "redirect:/user-register-student";
		}
		
	}
	
	
	@GetMapping("/show-student-to-delete/{id}")
	public ModelAndView requestStudentDeletionGet(@PathVariable("id")int student_id,Model model) {
		String success=(String) model.asMap().get("StudentDeleteReqSuccess");
		String error=(String) model.asMap().get("StudentUpdateError");
		
		if(success != null) {
			model.addAttribute("StudentUpdateSuccess", success);
		}
		if(error != null) {
			model.addAttribute("StudentDeleteReqError", error);
		}
		StudentDto studentdto=new StudentDto();
		Student student=new Student();
		List<StudentCourse> stucou=new ArrayList<StudentCourse>();
		List<String>cnames=new ArrayList<String>();
		Optional<Student> optionalStudent=Optional.ofNullable(studentservice.getStudentById(student_id));
		if(optionalStudent.isPresent()) {
			student=optionalStudent.get();
			studentdto.setId(student.getId());
			studentdto.setStudent_name(student.getStudentname());
			studentdto.setDob(student.getDob());
			studentdto.setGender(student.getGender());
			studentdto.setPhone(student.getPhone());
			studentdto.setEducation(student.getEducation());
			studentdto.setPhoto(student.getPhoto());
			studentdto.setRegisterDate(student.getRegisterDate());
			studentdto.setUsername(student.getUser().getUsername());
			studentdto.setUser_id(student.getUser().getId());
			stucou=student.getStudentcourse();
			
		}
		
		for(StudentCourse sc:stucou) {
			cnames.add(sc.getCourse().getCoursename());
		}
		List<Course> course_list=new ArrayList<Course>();
		course_list=courseservice.getAllCourses();
		model.addAttribute("cnames",cnames);
		model.addAttribute("course_list",course_list);
		return new ModelAndView("STU002-2","studentdto",studentdto);
	}
	
	@PostMapping("/user-delete-student")
	public String requestStudentDeletionPost(@ModelAttribute("studentdto")StudentDto studentdto, @RequestParam("user_message")String user_message, @RequestParam("course")String []course_list,Model model,RedirectAttributes redirectAttributes) {
		if (user_message == null) {
			model.addAttribute("userMessageEmptyError","Please type a reason.");
			model.addAttribute("studentdto", studentdto);
			return "STU002-2";
		}
		
		int stureqid=0;
		boolean stucouregisterreq=false;
		StudentRequest studentreq=new StudentRequest();
		studentreq.setStudentid(studentdto.getId());
		studentreq.setStudentname(studentdto.getStudent_name());
		studentreq.setDob(studentdto.getDob());
		studentreq.setGender(studentdto.getGender());
		studentreq.setPhone(studentdto.getPhone());
		studentreq.setEducation(studentdto.getEducation());
		studentreq.setPhoto(studentdto.getPhoto());
		studentreq.setAction("DELETE");
		studentreq.setStatus("PENDING");
		studentreq.setUser_message(user_message);
		
		LocalDate currentDate=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String requestDate = currentDate.format(formatter);
		studentreq.setRequestDate(requestDate);
		
		int user_id=studentdto.getUser_id();
		User user=new User();
		user=userservice.getUserById(user_id);
		studentreq.setUser(user);
		
		stureqid=studentreqservice.requestStudent(studentreq);
		
		//for course
		if(stureqid > 0) {
				
				Course course=new Course();
				
				
				if(course_list != null) {   //loop for each cname
					for(String cname:course_list) {
						StudentCourseRequest stucoursereq=new StudentCourseRequest();
						stucoursereq.setStudentid(studentreq.getStudentid());
						
						course=courseservice.getCourseByCname(cname);
						stucoursereq.setCourse(course);
						
						StudentRequest stureq=studentreqservice.getSreqById(stureqid);
						stucoursereq.setStudentRequest(stureq);
						
						if(stureq != null) {
						stucouregisterreq=stucoursereqservice.requestStudentCourse(stucoursereq);
						}
					}
					
					
					}
				if(stucouregisterreq) {
					redirectAttributes.addFlashAttribute("StudentDeleteReqSuccess", "You have requested student deletion successfully!");
				    
					}
					else {
						redirectAttributes.addFlashAttribute("StudentDeleteReqError","Something went wrong. Please request student deletion again.");
						
					}
				}
		
		
		return "redirect:/show-student-to-delete/"+studentdto.getId();
	}
	
	@GetMapping("/show-student-to-update/{id}")
	public ModelAndView requestStudentUpdateGet(@PathVariable("id")int student_id,Model model) {
		String success=(String) model.asMap().get("StudentUpdateSuccess");
		String error=(String) model.asMap().get("StudentUpdateError");
		
		if(success != null) {
			model.addAttribute("StudentUpdateSuccess", success);
		}
		if(error != null) {
			model.addAttribute("StudentUpdateError", error);
		}
		StudentDto studentdto=new StudentDto();
		Student student=new Student();
		List<StudentCourse> stucou=new ArrayList<StudentCourse>();
		List<String>cnames=new ArrayList<String>();
		Optional<Student> optionalStudent=Optional.ofNullable(studentservice.getStudentById(student_id));
		if(optionalStudent.isPresent()) {
			student=optionalStudent.get();
			studentdto.setId(student.getId());
			studentdto.setStudent_name(student.getStudentname());
			studentdto.setDob(student.getDob());
			studentdto.setGender(student.getGender());
			studentdto.setPhone(student.getPhone());
			studentdto.setEducation(student.getEducation());
			studentdto.setPhoto(student.getPhoto());
			studentdto.setRegisterDate(student.getRegisterDate());
			studentdto.setUsername(student.getUser().getUsername());
			studentdto.setUser_id(student.getUser().getId());
			stucou=student.getStudentcourse();
			
		}
		
		for(StudentCourse sc:stucou) {
			cnames.add(sc.getCourse().getCoursename());
		}
		List<Course> course_list=new ArrayList<Course>();
		course_list=courseservice.getAllCourses();
		model.addAttribute("cnames",cnames);
		model.addAttribute("course_list",course_list);
		return new ModelAndView("STU002-1","studentdto",studentdto);
	}
	
	@PostMapping("/user-update-student")
	public String requestStudentUpdatePost(@ModelAttribute("studentdto")@Validated StudentDto studentdto,@RequestParam("imageFile")MultipartFile imageFile, @RequestParam("course")String []course_list,Model model,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors() || course_list.length==0) {
			model.addAttribute("courseEmptyError","Please choose at lease one course.");
			model.addAttribute("studentdto", studentdto);
			return "STU002-1";
		}
		
		boolean updateStudentCourse=false;
		Student student=new Student();
		student.setId(studentdto.getId());
		student.setStudentname(studentdto.getStudent_name());
		student.setDob(studentdto.getDob());
		student.setGender(studentdto.getGender());
		student.setPhone(studentdto.getPhone());
		student.setEducation(studentdto.getEducation());
		student.setPhoto(studentdto.getPhoto());
		student.setRegisterDate(studentdto.getRegisterDate());
		
		int user_id=studentdto.getUser_id();
		User user=userservice.getUserById(user_id);
		student.setUser(user);
		System.out.print("imageFile"+imageFile);
		MultipartFile multipartFile=imageFile;
		if(multipartFile !=null && !multipartFile.isEmpty()) {
			
			String uniqueName=UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
			String fileName="D:\\studentmanagmentsb\\src\\main\\resources\\static\\image\\"+uniqueName;
			try {
				multipartFile.transferTo(new File(fileName));
				
				student.setPhoto(uniqueName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//update student table
		boolean updateStudent=studentservice.createOrUpdateStudent(student);
		
		//delete courses first
		List<StudentCourse> stucoulist=stucouservice.getSCByStudent(student);
		boolean deleteStudentCourse=false;
		Course course=new Course();
		
		for(StudentCourse sc:stucoulist) {
			deleteStudentCourse=stucouservice.deleteSCById(sc.getId());
			
		}
		
		
		if(deleteStudentCourse) {
			if(course_list != null) {
				for(String cname: course_list) {
					StudentCourse stucou=new StudentCourse();
					stucou.setStudent(student);
					
					course=courseservice.getCourseByCname(cname);
					stucou.setCourse(course);
					
				 updateStudentCourse=stucouservice.createStudentCourse(stucou);
				}
			}
		}
		
		
		if(updateStudent && updateStudentCourse) {
			redirectAttributes.addFlashAttribute("StudentUpdateSuccess", "You have updated student successfully!");
			return "redirect:/show-student-to-update/"+student.getId();
		}
		else {
			redirectAttributes.addFlashAttribute("StudentUpdateError","Something went wrong. Please update student again.");
			return "redirect:/show-student-to-update/"+student.getId();
		}
	}
	
	@GetMapping("/view-request-history")
	public String showAllStudentRequests(Model model,HttpSession session) {
		User loginUser=(User) session.getAttribute("loginUser");
		User user=userservice.getUserById(loginUser.getId());
		List<StudentRequest> stureq_list=studentreqservice.getStureqByUser(user);
		model.addAttribute("stureq_list", stureq_list);
		return "USR005";
	}
	
	@GetMapping("/show-one-student-request/{request_id}")
	public ModelAndView showOneStudentRequest(@PathVariable("request_id")int request_id, Model model) {
		StudentRequest stureq=studentreqservice.getSreqById(request_id);

		StudentRequestDto stureqdto=new StudentRequestDto();
		stureqdto.setId(stureq.getId());
		stureqdto.setStudent_id(stureq.getStudentid());
		stureqdto.setStudent_name(stureq.getStudentname());
		stureqdto.setDob(stureq.getDob());
		stureqdto.setGender(stureq.getGender());
		stureqdto.setPhone(stureq.getPhone());
		stureqdto.setEducation(stureq.getEducation());
		stureqdto.setPhoto(stureq.getPhoto());
		stureqdto.setAction(stureq.getAction());
		stureqdto.setStatus(stureq.getStatus());
		stureqdto.setRequestDate(stureq.getRequestDate());
		stureqdto.setUser_message(stureq.getUser_message());
		

		List<StudentCourseRequest> stucourse_list=stureq.getStudentcourserequest();
		List<String> cnames = new ArrayList<String>();
		for(StudentCourseRequest stucourse:stucourse_list) {
			cnames.add(stucourse.getCourse().getCoursename());
		}
		List<Course> course_list=courseservice.getAllCourses();
		model.addAttribute("course_list",course_list);
		model.addAttribute("cnames", cnames);
		return new ModelAndView("USR006","stureqdto",stureqdto);
	}
	
	@PostMapping("/request-history-search")
	public String adminSearchRequest(Model model,HttpSession session,@RequestParam("studentid")String studentid,@RequestParam("studentname")String sname,@RequestParam("action")String action,@RequestParam("status")String status,@RequestParam("coursename")String cname) {
		int sid=-1;
		List<StudentRequest> stureqList=new ArrayList<StudentRequest> ();
		
		User user=(User) session.getAttribute("loginUser");
		
		if(!studentid.equals("")) {
			sid=Integer.parseInt(studentid);
		}
	
		if(sname.equals("")) {
			sname=null;
		}
		if(action.equals("")) {
			action=null;
		}
		if(status.equals("")) {
			status=null;
		}
		if(cname.equals("")) {
			cname=null;
		}
		if(sid>=0 || sname != null || action != null || status != null || cname != null ) {
			if(sid >=0 && sname != null && action != null && status != null && cname !=null) {
				stureqList=studentreqservice.searchByAll(user,sid, sname, action, status, cname);
			}
			
			else if(sid >=0 && sname != null && action ==null && status == null && cname== null) {
				stureqList=studentreqservice.searchBySidSname(user,sid, sname);
			}
			else if(sid>=0 && sname==null && action ==null && status == null && cname== null) {
				stureqList=studentreqservice.searchBySid(user,sid);
			}
			else if(sid>=0 && sname==null && action !=null && status == null && cname== null) {
				stureqList=studentreqservice.searchBySidAction(user,sid, action);
			}
			else if(sid>=0 && sname==null && action ==null && status != null && cname== null) {
				stureqList=studentreqservice.searchBySidStatus(user,sid, status);
			}
			else if(sid>=0 && sname==null && action ==null && status == null && cname!= null) {
				stureqList=studentreqservice.searchByIdCname(user,sid, cname);
			}
			
			else if(sid>=0 && sname!=null && action ==null && status == null && cname!= null) {
				stureqList=studentreqservice.searchBySidSnameCname(user,sid, sname, cname);
			}
			
			else if(sid>=0 && sname==null && action !=null && status != null && cname!= null) {
				stureqList=studentreqservice.searchBySidActionStatus(user,sid, action, status);
			}
			
			else if(sid <0 && sname==null && action!=null && status != null && cname !=null) {
				stureqList=studentreqservice.searchByActionStatusCname(user,action, status, cname);
			}
			else if(sid <0 && sname==null && action==null && status != null && cname !=null) {
				stureqList=studentreqservice.searchByStatusCname(user,status, cname);
			}
			else if(sid <0 && sname==null && action==null && status == null && cname !=null) {
				stureqList=studentreqservice.searchByCname(user,cname);
			}
			else if(sid <0 && sname!=null && action==null && status == null && cname !=null) {
				stureqList=studentreqservice.searchBySnameCname(user,sname, cname);
			}
			else if(sid <0 && sname==null && action==null && status != null && cname ==null) {
				stureqList=studentreqservice.searchByStatus(user,status);
			}
			else if(sid <0 && sname==null && action!=null && status == null && cname ==null) {
				stureqList=studentreqservice.searchByAction(user,action);
			}
			else if(sid <0 && sname==null && action!=null && status != null && cname ==null) {
				stureqList=studentreqservice.searchByActionStatus(user,action, status);
			}
			else if(sid <0 && sname!=null && action !=null && status !=null && cname==null) {
				stureqList=studentreqservice.searchBySnameActionStatus(user,sname, action, status);
			}
			else if(sid <0 && sname!=null && action ==null && status !=null && cname==null) {
				stureqList=studentreqservice.searchBySnameStatus(user,sname, status);
			}
			else if(sid <0 && sname!=null && action !=null && status ==null && cname==null) {
				stureqList=studentreqservice.searchBySnameAction(user,sname, action);
			}
			else if(sid <0 && sname==null && action !=null && status ==null && cname!=null) {
				stureqList=studentreqservice.searchByActionCname(user,action, cname);
			}
			else if(sid <0 && sname==null && action ==null && status !=null && cname!=null) {
				stureqList=studentreqservice.searchByStatusCname(user,status, cname);
			}
			else if(sid <0 && sname!=null && action ==null && status ==null && cname==null) {
				stureqList=studentreqservice.searchBySname(user,sname);
			}
			if(!stureqList.isEmpty() ) {
			model.addAttribute("stureqList",stureqList);}
			else {
				model.addAttribute("searchError", "Student not found!");
			}
			return "USR007";
		}
		else {
			
			return "redirect:/view-request-history";
		}
	}
}
