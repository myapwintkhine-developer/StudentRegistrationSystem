package com.ojt.studentmanagmentsb.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ojt.studentmanagmentsb.dto.*;
import com.ojt.studentmanagmentsb.entity.*;
import com.ojt.studentmanagmentsb.service.*;



@Controller
public class AdminStudentController {
	@Autowired StudentRequestService stureqservice;
	@Autowired StudentService studentservice;
	@Autowired UserService userservice;
	@Autowired CourseService courseservice;
	@Autowired StudentCourseService stucouservice;
	@Autowired StudentCourseRequestService stucoureqservice;
	
	@GetMapping("/admin-view-requests")
	public String adminViewRequests(Model model) {
		String approve=(String) model.asMap().get("adminApproveSuccess");
		String reject=(String) model.asMap().get("adminRejectSuccess");
		if(approve != null) {
			model.addAttribute("adminApproveSuccess", approve);
		}
		if(reject != null) {
			model.addAttribute("adminRejectSuccess", reject);
		}
		List<StudentRequest> stureq_list=stureqservice.getAllStuRequests();
		model.addAttribute("stureq_list", stureq_list);
		return "ADM002";
	}
	
	@GetMapping("/admin-view-one-request/{request_id}")
	public ModelAndView adminViewOneRequest(@PathVariable("request_id")int request_id, Model model) {
		StudentRequest stureq=stureqservice.getSreqById(request_id);
		
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
		stureqdto.setUser_id(stureq.getUser().getId());
		stureqdto.setUsername(stureq.getUser().getUsername());
		stureqdto.setAdmin_reply(stureq.getAdmin_reply());
		
		List<StudentCourseRequest> stucourse_list=stureq.getStudentcourserequest();
		List<String> cnames = new ArrayList<>();
		for(StudentCourseRequest stucourse:stucourse_list) {
			cnames.add(stucourse.getCourse().getCoursename());
		}
		List<Course> course_list=courseservice.getAllCourses();
		model.addAttribute("course_list",course_list);
		model.addAttribute("cnames", cnames);
		return new ModelAndView("ADM003","stureqdto",stureqdto);
	}
	
	@PostMapping("/admin-confirm")
	public String adminDecide(@ModelAttribute("stureqdto")StudentRequestDto stureqdto,@RequestParam("buttonaction")String buttonaction, @RequestParam("course")String []course_list,RedirectAttributes redirectAttributes) {
		Student student=new Student();
		if(buttonaction.equals("approve")) {
			if(stureqdto.getAction().equals("INSERT") && stureqdto.getStatus().equals("PENDING")) {
				student.setStudentname(stureqdto.getStudent_name());
				student.setDob(stureqdto.getDob());
				student.setGender(stureqdto.getGender());
				student.setEducation(stureqdto.getEducation());
				student.setPhone(stureqdto.getPhone());
				student.setPhoto(stureqdto.getPhoto());
				student.setRegisterDate(stureqdto.getRequestDate());
				
				int user_id=stureqdto.getUser_id();
				User user=new User();
				user=userservice.getUserById(user_id);
				student.setUser(user);
				
				//for course
				Course course=new Course();
				
				int student_id=studentservice.createStudent(student);
				if(student_id > 0) {
				if(course_list != null) {
					for(String cname:course_list) {
						StudentCourse stucou=new StudentCourse();
						course=courseservice.getCourseByCname(cname);
						
						stucou.setCourse(course);
						
						student.setId(student_id);
						stucou.setStudent(student);
						
						stucouservice.createStudentCourse(stucou);
						
					}
				}
				}
				
				//for student request
				int request_id=stureqdto.getId();
				StudentRequest studentreq=stureqservice.getSreqById(request_id);
				studentreq.setStudentid(student_id);
				studentreq.setStatus("APPROVED");
				studentreq.setAdmin_reply(stureqdto.getAdmin_reply());
				
				request_id=stureqservice.requestStudent(studentreq);
				if(request_id > 0) {
					List<StudentCourseRequest> stucoureq_list=stucoureqservice.getSCreqBySreq(studentreq);
					for(StudentCourseRequest stucoureq:stucoureq_list) {
						stucoureq.setStudentid(student_id);
						stucoureqservice.requestStudentCourse(stucoureq);
					}
					
					redirectAttributes.addFlashAttribute("adminApproveSuccess", "You have successfully approved one request!");
				}
				
				
			}
			
			if(stureqdto.getAction().equals("DELETE") && stureqdto.getStatus().equals("PENDING")) {
				int studentid=stureqdto.getStudent_id();
				student=studentservice.getStudentById(studentid);
				List<StudentCourse> stucou=stucouservice.getSCByStudent(student);
				for(StudentCourse sc:stucou) {
				boolean	deleteSC=stucouservice.deleteSCById(sc.getId());
				}
				boolean deleteStudent=studentservice.deleteStudentById(student.getId());
				StudentRequest stureq=stureqservice.getSreqById(stureqdto.getId());
				stureq.setStatus("APPROVED");
				stureq.setAdmin_reply(stureq.getAdmin_reply());
				
				int reqid=stureqservice.requestStudent(stureq);
				if(deleteStudent && reqid>0) {
				
				redirectAttributes.addFlashAttribute("adminApproveSuccess", "You have successfully approved one request!");
				}
			}
			
		}
		
		else if(buttonaction.equals("reject")) {
			int request_id=stureqdto.getId();
			StudentRequest studentreq=stureqservice.getSreqById(request_id);
			studentreq.setId(request_id);
			studentreq.setStatus("REJECTED");
			studentreq.setAdmin_reply(stureqdto.getAdmin_reply());
			stureqservice.requestStudent(studentreq);
			
			redirectAttributes.addFlashAttribute("adminRejectSuccess", "You have successfully rejected one request!");
			
		}
		
		else if(buttonaction.equals("back")) {
			return "redirect:/admin-view-requests";
		}
	
		return "redirect:/admin-view-requests";
	}
	
	@PostMapping("/admin-search-request")
	public String adminSearchRequest(Model model,@RequestParam("studentid")String studentid,@RequestParam("studentname")String sname,@RequestParam("action")String action,@RequestParam("status")String status,@RequestParam("coursename")String cname) {
		int sid=-1;
		List<StudentRequest> stureqList=new ArrayList<StudentRequest> ();
		
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
				stureqList=stureqservice.searchByAll(sid, sname, action, status, cname);
			}
			
			else if(sid >=0 && sname != null && action ==null && status == null && cname== null) {
				stureqList=stureqservice.searchBySidSname(sid, sname);
			}
			else if(sid>=0 && sname==null && action ==null && status == null && cname== null) {
				stureqList=stureqservice.getStureqBySid(sid);
			}
			else if(sid>=0 && sname==null && action !=null && status == null && cname== null) {
				stureqList=stureqservice.searchBySidAction(sid, action);
			}
			else if(sid>=0 && sname==null && action ==null && status != null && cname== null) {
				stureqList=stureqservice.searchBySidStatus(sid, status);
			}
			else if(sid>=0 && sname==null && action ==null && status == null && cname!= null) {
				stureqList=stureqservice.searchByIdCname(sid, cname);
			}
			
			else if(sid>=0 && sname!=null && action ==null && status == null && cname!= null) {
				stureqList=stureqservice.searchBySidSnameCname(sid, sname, cname);
			}
			
			else if(sid>=0 && sname==null && action !=null && status != null && cname!= null) {
				stureqList=stureqservice.searchBySidActionStatus(sid, action, status);
			}
			
			else if(sid <0 && sname==null && action!=null && status != null && cname !=null) {
				stureqList=stureqservice.searchByActionStatusCname(action, status, cname);
			}
			else if(sid <0 && sname==null && action==null && status != null && cname !=null) {
				stureqList=stureqservice.searchByStatusCname(status, cname);
			}
			else if(sid <0 && sname==null && action==null && status == null && cname !=null) {
				stureqList=stureqservice.searchByCname(cname);
			}
			else if(sid <0 && sname!=null && action==null && status == null && cname !=null) {
				stureqList=stureqservice.searchBySnameCname(sname, cname);
			}
			else if(sid <0 && sname==null && action==null && status != null && cname ==null) {
				stureqList=stureqservice.searchByStatus(status);
			}
			else if(sid <0 && sname==null && action!=null && status == null && cname ==null) {
				stureqList=stureqservice.searchByAction(action);
			}
			else if(sid <0 && sname==null && action!=null && status != null && cname ==null) {
				stureqList=stureqservice.searchByActionStatus(action, status);
			}
			else if(sid <0 && sname!=null && action !=null && status !=null && cname==null) {
				stureqList=stureqservice.searchBySnameActionStatus(sname, action, status);
			}
			else if(sid <0 && sname!=null && action ==null && status !=null && cname==null) {
				stureqList=stureqservice.searchBySnameStatus(sname, status);
			}
			else if(sid <0 && sname!=null && action !=null && status ==null && cname==null) {
				stureqList=stureqservice.searchBySnameAction(sname, action);
			}
			else if(sid <0 && sname==null && action !=null && status ==null && cname!=null) {
				stureqList=stureqservice.searchByActionCname(action, cname);
			}
			else if(sid <0 && sname==null && action ==null && status !=null && cname!=null) {
				stureqList=stureqservice.searchByStatusCname(status, cname);
			}
			else if(sid <0 && sname!=null && action ==null && status ==null && cname==null) {
				stureqList=stureqservice.searchBySname(sname);
			}
			if(!stureqList.isEmpty() ) {
				model.addAttribute("stureqList",stureqList);}
				else {
					model.addAttribute("searchError", "Student not found!");
				}
				return "ADM004";
			
		}
		else {
			return "redirect:/admin-view-requests";
		}
	}
	
}

