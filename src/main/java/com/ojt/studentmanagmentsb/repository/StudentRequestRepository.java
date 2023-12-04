package com.ojt.studentmanagmentsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.Student;
import com.ojt.studentmanagmentsb.entity.StudentRequest;
import com.ojt.studentmanagmentsb.entity.User;


@Repository
public interface StudentRequestRepository extends JpaRepository<StudentRequest, Integer>{
	List<StudentRequest> findByStudentid(int studentid);
	
	List<StudentRequest> findByUser(User user);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentname LIKE %:studentname%")
	List<StudentRequest> findRequestByStudentname(@Param("studentname")String studentname);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.action LIKE %:action%")
	List<StudentRequest> findRequestByAction(@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.status LIKE %:status%")
	List<StudentRequest> findRequestByStatus(@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByCoursename(@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentid=:studentid AND s.studentname LIKE %:studentname%")
	List<StudentRequest> findRequestByIdAndStudentname(@Param("studentid")int id,@Param("studentname")String studentname);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByActionAndStatus(@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentid=:studentid AND s.action LIKE %:action%")
	List<StudentRequest> findRequestByIdAndAction(@Param("studentid")int id,@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentid=:studentid AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByIdAndStatus(@Param("studentid")int id,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentid=:studentid AND s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByIdAndActionAndStatus(@Param("studentid")int id,@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentname LIKE %:studentname% AND s.action LIKE %:action%")
	List<StudentRequest> findRequestByStudentnameAndAction(@Param("studentname")String studentname,@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentname LIKE %:studentname% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByStudentnameAndStatus(@Param("studentname")String studentname,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.studentname LIKE %:studentname% AND s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByStudentnameAndActionAndStatus(@Param("studentname")String studentname,@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentnameAndCoursename(@Param("studentname") String studentname,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.action LIKE %:action% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByActionAndCoursename(@Param("action") String action,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStatusAndCoursename(@Param("status") String status,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.action LIKE %:action% AND s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByActionAndStatusAndCoursename(@Param("action") String action,@Param("status") String status,@Param("coursename") String coursename);
	
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.studentid=:studentid AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentidAndCoursename(@Param("studentid") int studentid,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.studentid = :studentid AND s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentidAndNameAndCoursename(@Param("studentid") int studentid,
                                                      @Param("studentname") String studentname,
                                                      @Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.studentid = :studentid AND s.studentname LIKE %:studentname% AND s.action LIKE %:action% AND s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByIdNameActionStatusCourse(@Param("studentid") int studentid,
                                                      @Param("studentname") String studentname,@Param("action") String action,@Param("status") String status,
                                                      @Param("coursename") String coursename);
	
	//for history search
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentid =:studentid")
	List<StudentRequest> findRequestByStudentid(@Param("user")User user,@Param("studentid")int studentid);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentname LIKE %:studentname%")
	List<StudentRequest> findRequestByStudentname(@Param("user")User user,@Param("studentname")String studentname);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.action LIKE %:action%")
	List<StudentRequest> findRequestByAction(@Param("user")User user,@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByStatus(@Param("user")User user,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByCoursename(@Param("user")User user,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentid=:studentid AND s.studentname LIKE %:studentname%")
	List<StudentRequest> findRequestByIdAndStudentname(@Param("user")User user,@Param("studentid")int id,@Param("studentname")String studentname);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByActionAndStatus(@Param("user")User user,@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentid=:studentid AND s.action LIKE %:action%")
	List<StudentRequest> findRequestByIdAndAction(@Param("user")User user,@Param("studentid")int id,@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentid=:studentid AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByIdAndStatus(@Param("user")User user,@Param("studentid")int id,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentid=:studentid AND s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByIdAndActionAndStatus(@Param("user")User user,@Param("studentid")int id,@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentname LIKE %:studentname% AND s.action LIKE %:action%")
	List<StudentRequest> findRequestByStudentnameAndAction(@Param("user")User user,@Param("studentname")String studentname,@Param("action")String action);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentname LIKE %:studentname% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByStudentnameAndStatus(@Param("user")User user,@Param("studentname")String studentname,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s WHERE s.user=:user AND s.studentname LIKE %:studentname% AND s.action LIKE %:action% AND s.status LIKE %:status%")
	List<StudentRequest> findRequestByStudentnameAndActionAndStatus(@Param("user")User user,@Param("studentname")String studentname,@Param("action")String action,@Param("status")String status);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentnameAndCoursename(@Param("user")User user,@Param("studentname") String studentname,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.action LIKE %:action% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByActionAndCoursename(@Param("user")User user,@Param("action") String action,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStatusAndCoursename(@Param("user")User user,@Param("status") String status,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.action LIKE %:action% AND s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByActionAndStatusAndCoursename(@Param("user")User user,@Param("action") String action,@Param("status") String status,@Param("coursename") String coursename);
	
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.studentid=:studentid AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentidAndCoursename(@Param("user")User user,@Param("studentid") int studentid,@Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.studentid = :studentid AND s.studentname LIKE %:studentname% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByStudentidAndNameAndCoursename(@Param("user")User user,@Param("studentid") int studentid,
                                                      @Param("studentname") String studentname,
                                                      @Param("coursename") String coursename);
	
	@Query("SELECT s FROM StudentRequest s JOIN s.studentcourserequest sc JOIN sc.course c WHERE s.user=:user AND s.studentid = :studentid AND s.studentname LIKE %:studentname% AND s.action LIKE %:action% AND s.status LIKE %:status% AND c.coursename LIKE %:coursename%")
    List<StudentRequest> findRequestByIdNameActionStatusCourse(@Param("user")User user,@Param("studentid") int studentid,
                                                      @Param("studentname") String studentname,@Param("action") String action,@Param("status") String status,
                                                      @Param("coursename") String coursename);
	
}
