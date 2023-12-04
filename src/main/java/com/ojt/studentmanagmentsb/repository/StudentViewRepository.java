package com.ojt.studentmanagmentsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojt.studentmanagmentsb.entity.StudentView;

@Repository
public interface StudentViewRepository extends JpaRepository<StudentView, Integer>{

}
