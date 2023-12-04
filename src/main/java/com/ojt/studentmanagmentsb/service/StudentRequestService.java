package com.ojt.studentmanagmentsb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.StudentRequestRepository;



@Service
public class StudentRequestService {
	@Autowired StudentRequestRepository studentreqrep;
	
	public int requestStudent(StudentRequest studentreq) {
		StudentRequest savedStudentReq=studentreqrep.save(studentreq);
		return savedStudentReq.getId();
	}
	
	public List<StudentRequest> getAllStuRequests(){
		return studentreqrep.findAll();
	}
	
	
	public StudentRequest getSreqById(int studentreq_id) {
		Optional<StudentRequest> studentreq=studentreqrep.findById(studentreq_id);
		return studentreq.orElse(null);
	}
	
	public List<StudentRequest> getStureqBySid(int student_id) {
		return studentreqrep.findByStudentid(student_id);
	}
	
	public List<StudentRequest> getStureqByUser(User user){
		return studentreqrep.findByUser(user);
	}
	
	public List<StudentRequest> searchBySname(String studentname){
		return studentreqrep.findRequestByStudentname(studentname);
	}
	
	public List<StudentRequest> searchByAction(String action){
		return studentreqrep.findRequestByAction(action);
	}
	
	public List<StudentRequest> searchByStatus(String status){
		return studentreqrep.findRequestByStatus(status);
	}
	
	public List<StudentRequest> searchByCname(String cname){
		return studentreqrep.findRequestByCoursename(cname);
	}
	
	public List<StudentRequest> searchBySidSname(int studentid,String studentname){
		return studentreqrep.findRequestByIdAndStudentname(studentid,studentname);
	}
	
	public List<StudentRequest> searchByActionStatus(String action,String status){
		return studentreqrep.findRequestByActionAndStatus(action, status);
	}
	
	public List<StudentRequest> searchBySnameActionStatus(String studentname,String action,String status){
		return studentreqrep.findRequestByStudentnameAndActionAndStatus(studentname, action, status);
	}
	
	public List<StudentRequest> searchBySidActionStatus(int studentid,String action,String status){
		return studentreqrep.findRequestByIdAndActionAndStatus(studentid, action, status);
	}
	
	public List<StudentRequest> searchBySnameCname(String studentname,String cname){
		return studentreqrep.findRequestByStudentnameAndCoursename(studentname, cname);
	}
	
	public List<StudentRequest> searchByActionCname(String action,String cname){
		return studentreqrep.findRequestByActionAndCoursename(action, cname);
	}
	
	public List<StudentRequest> searchByStatusCname(String status,String cname){
		return studentreqrep.findRequestByStatusAndCoursename(status, cname);
	}
	
	public List<StudentRequest> searchByIdCname(int studentid,String cname){
		return studentreqrep.findRequestByStudentidAndCoursename(studentid, cname);
	}
	
	public List<StudentRequest> searchByActionStatusCname(String action,String status,String cname){
		return studentreqrep.findRequestByActionAndStatusAndCoursename(action, status, cname);
	}
	
	public List<StudentRequest> searchByAll(int studentid, String name,String action,String status,String cname){
		return studentreqrep.findRequestByIdNameActionStatusCourse(studentid, name, action, status, cname);
	}
	
	public List<StudentRequest> searchBySidAction(int studentid,String action){
		return studentreqrep.findRequestByIdAndAction(studentid, action);
	}
	
	public List<StudentRequest> searchBySidStatus(int studentid,String status){
		return studentreqrep.findRequestByIdAndStatus(studentid, status);
	}
	
	public List<StudentRequest> searchBySidSnameCname(int studentid,String name,String cname){
		return studentreqrep.findRequestByStudentidAndNameAndCoursename(studentid, name, cname);
	}
	
	public List<StudentRequest> searchBySnameAction(String name,String action){
		return studentreqrep.findRequestByStudentnameAndAction(name, action);
	}
	
	public List<StudentRequest> searchBySnameStatus(String name,String status){
		return studentreqrep.findRequestByStudentnameAndStatus(name, status);
	}
	
//for history search
	public List<StudentRequest> searchBySid(User user,int studentid){
		return studentreqrep.findRequestByStudentid(user,studentid);
	}
	public List<StudentRequest> searchBySname(User user,String studentname){
		return studentreqrep.findRequestByStudentname(user,studentname);
	}
	
	public List<StudentRequest> searchByAction(User user,String action){
		return studentreqrep.findRequestByAction(user,action);
	}
	
	public List<StudentRequest> searchByStatus(User user,String status){
		return studentreqrep.findRequestByStatus(user,status);
	}
	
	public List<StudentRequest> searchByCname(User user,String cname){
		return studentreqrep.findRequestByCoursename(user,cname);
	}
	
	public List<StudentRequest> searchBySidSname(User user,int studentid,String studentname){
		return studentreqrep.findRequestByIdAndStudentname(user,studentid,studentname);
	}
	
	public List<StudentRequest> searchByActionStatus(User user,String action,String status){
		return studentreqrep.findRequestByActionAndStatus(user,action, status);
	}
	
	public List<StudentRequest> searchBySnameActionStatus(User user,String studentname,String action,String status){
		return studentreqrep.findRequestByStudentnameAndActionAndStatus(user,studentname, action, status);
	}
	
	public List<StudentRequest> searchBySidActionStatus(User user,int studentid,String action,String status){
		return studentreqrep.findRequestByIdAndActionAndStatus(user,studentid, action, status);
	}
	
	public List<StudentRequest> searchBySnameCname(User user,String studentname,String cname){
		return studentreqrep.findRequestByStudentnameAndCoursename(user,studentname, cname);
	}
	
	public List<StudentRequest> searchByActionCname(User user,String action,String cname){
		return studentreqrep.findRequestByActionAndCoursename(user,action, cname);
	}
	
	public List<StudentRequest> searchByStatusCname(User user,String status,String cname){
		return studentreqrep.findRequestByStatusAndCoursename(user,status, cname);
	}
	
	public List<StudentRequest> searchByIdCname(User user,int studentid,String cname){
		return studentreqrep.findRequestByStudentidAndCoursename(user,studentid, cname);
	}
	
	public List<StudentRequest> searchByActionStatusCname(User user,String action,String status,String cname){
		return studentreqrep.findRequestByActionAndStatusAndCoursename(user,action, status, cname);
	}
	
	public List<StudentRequest> searchByAll(User user,int studentid, String name,String action,String status,String cname){
		return studentreqrep.findRequestByIdNameActionStatusCourse(user,studentid, name, action, status, cname);
	}
	
	public List<StudentRequest> searchBySidAction(User user,int studentid,String action){
		return studentreqrep.findRequestByIdAndAction(user,studentid, action);
	}
	
	public List<StudentRequest> searchBySidStatus(User user,int studentid,String status){
		return studentreqrep.findRequestByIdAndStatus(user,studentid, status);
	}
	
	public List<StudentRequest> searchBySidSnameCname(User user,int studentid,String name,String cname){
		return studentreqrep.findRequestByStudentidAndNameAndCoursename(user,studentid, name, cname);
	}
	
	public List<StudentRequest> searchBySnameAction(User user,String name,String action){
		return studentreqrep.findRequestByStudentnameAndAction(user,name, action);
	}
	
	public List<StudentRequest> searchBySnameStatus(User user,String name,String status){
		return studentreqrep.findRequestByStudentnameAndStatus(user,name, status);
	}
	
}

