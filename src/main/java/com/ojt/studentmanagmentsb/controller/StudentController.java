package com.ojt.studentmanagmentsb.controller;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojt.studentmanagmentsb.service.CourseService;
import com.ojt.studentmanagmentsb.service.StudentRequestService;
import com.ojt.studentmanagmentsb.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentCourse;
import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.entity.StudentView;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.entity.UserView;
import com.ojt.studentmanagmentsb.entity.Course;

@Controller
public class StudentController {
	@Autowired StudentRequestService stureqservice;
	@Autowired StudentService studentservice;
	@Autowired CourseService courseservice;
	
	@GetMapping("/view-all-students")
	public String viewStudents(Model model,HttpSession session) {
		User loginUser=(User) session.getAttribute("loginUser");
		if(loginUser == null) {
			return "redirect:/login";
		}
		else {
		List<Student>student_list=studentservice.getAllStudents();
		model.addAttribute("student_list", student_list);
		return "STU003";
		}
	}
	
	@GetMapping("/view-one-student/{id}")
	public String viewOneStudent(@PathVariable("id") int id, Model model,HttpSession session) {
		
		User loginUser=(User) session.getAttribute("loginUser");
		if(loginUser == null) {
			return "redirect:/login";
		}else {
		
		Student student=new Student();
		Optional<Student> optionalStudent=Optional.ofNullable(studentservice.getStudentById(id));
		if(optionalStudent.isPresent()) {
			student=optionalStudent.get();
		}
		List<String> cnames=new ArrayList<String>();
		for(StudentCourse sc: student.getStudentcourse()) {
			cnames.add(sc.getCourse().getCoursename());
		}
		
		List<Course> course_list=new ArrayList<Course>();
		course_list=courseservice.getAllCourses();
		
		List<StudentRequest> stureqlist=stureqservice.getStureqBySid(student.getId());
		List<String> statuslist=new ArrayList<String>();
		for(StudentRequest stureq:stureqlist) {
			statuslist.add(stureq.getStatus());
		}
		model.addAttribute("statuslist", statuslist);
		model.addAttribute("cnames", cnames);
		model.addAttribute("one_student", student);
		model.addAttribute("course_list",course_list);
		return "STU002";
		}
	}
	
	@PostMapping("/student-search")
	public String searchStudent(@RequestParam("id")String sid,@RequestParam("studentname")String studentname,@RequestParam("coursename")String coursename,Model model) {
		Student searchStudent=null;
		List<Student> searchStudentList=new ArrayList<Student>();
		int id=0;
		if(studentname.equals("")) {
			studentname=null;
		}
		if(coursename.equals("")) {
			coursename=null;
		}
		if(!sid.equals("")) {
			id=(Integer.parseInt(sid));
		}
		
		if(!sid.equals("") || studentname != null || coursename != null) {
			if(id > 0 && studentname != null && coursename != null) {
				searchStudentList=(List<Student>) studentservice.searchStudentByIdSnameCname(id, studentname, coursename);
				if(searchStudentList != null) {
					model.addAttribute("searchStudentList",searchStudentList);
				}else {
					model.addAttribute("searchError","Student not found!");
				}
			}
			
			
			else if(id >0 && studentname != null && coursename == null) {
			searchStudent=studentservice.searchStudentByIdName(id, studentname);
			if(searchStudent != null) {
				model.addAttribute("searchStudent",searchStudent);
			}else {
				model.addAttribute("searchError","Student not found!");
			}
		}
		
			else if(id >0 && studentname == null && coursename != null) {
			searchStudentList=(List<Student>) studentservice.searchStudentByIdCname(id, coursename);
			if(searchStudentList != null) {
				model.addAttribute("searchStudentList",searchStudentList);
			}else {
				model.addAttribute("searchError","Student not found!");
			}
		}
			
			else if(id>0 && studentname == null && coursename == null) {
				searchStudent=studentservice.getStudentById(id);
				if(searchStudent != null) {
					model.addAttribute("searchStudent",searchStudent);
				}else {
					model.addAttribute("searchError","Student not found!");
				}
			}
			
			else if(id <= 0 && studentname != null && coursename != null) {
				searchStudentList=studentservice.searchStudentBySnameCname(studentname, coursename);
				if(searchStudentList != null) {
					model.addAttribute("searchStudentList",searchStudentList);
				}else {
					model.addAttribute("searchError","Student not found!");
				}
			}
			
			else if(id <=0 && studentname != null && coursename == null) {
				searchStudentList=studentservice.searchStudentBySname(studentname);
				if(searchStudentList != null) {
					model.addAttribute("searchStudentList",searchStudentList);
				}else {
					model.addAttribute("searchError","Student not found!");
				}
			}
			
			else if(id <= 0 && studentname == null && coursename != null) {
				searchStudentList=studentservice.searchStudentByCname(coursename);
				if(searchStudentList != null) {
					model.addAttribute("searchStudentList",searchStudentList);
				}else {
					model.addAttribute("searchError","Student not found!");
				}
			}
		
			return "STU004";
		}
		
		else {
			model.addAttribute("searchError", "Student not found!");
			return "redirect:/view-all-students";
		}
		
		
	}
	
	@GetMapping("/download-student-report/{type}")
	public void downloadReport(@PathVariable("type") String type, HttpServletResponse response, HttpServletRequest request) throws IOException, JRException {
	    ClassPathResource resource = new ClassPathResource("studentreport.jrxml");
	    String path = resource.getFile().getAbsolutePath();

	    // Retrieve data for the report (assuming you have a service to fetch the data)
	    List<StudentView> list = studentservice.getStudentForReport();
	  
	    // Parameters for the report
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("ReportTitle", "Student List");

	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

	    JasperReport jasperReport = JasperCompileManager.compileReport(path);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

	    if (type.equalsIgnoreCase("pdf")) {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=studentreport.pdf");

	        OutputStream out = response.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	        out.flush();
	        out.close();
	    } else if (type.equalsIgnoreCase("excel")) {
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=studentreport.xlsx");

	        JRXlsxExporter exporter = new JRXlsxExporter();
	        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
	        
	        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		    configuration.setDetectCellType(true);
		    configuration.setRemoveEmptySpaceBetweenColumns(true);
		    configuration.setRemoveEmptySpaceBetweenRows(true);
		    exporter.setConfiguration(configuration);
		    configuration.setWhitePageBackground(false);
		    configuration.setCollapseRowSpan(false);
		    configuration.setIgnoreGraphics(false);
	        exporter.exportReport();
	    } else {
	        // Handle invalid type parameter
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type");
	    }
	}
}
