package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ojt.studentmanagmentsb.entity.Reset;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.ResetRepository;
import com.ojt.studentmanagmentsb.service.ResetService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestResetService {
	
	@Mock
	private ResetRepository resetrepo;
	
	 @InjectMocks
	 private ResetService resetservice;

	@Test
	public void testGetResetByStatusUser() {
		User user1=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user2=new User(2,"aye","aye@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user3=new User(3,"su","su@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	
    	Reset rs1=new Reset(1,user3,"dowerwoure-329473294-adfoduidr","2023-06-30T10:44:01.840866300","PENDING");
    	Reset rs2=new Reset(2,user1,"dowerwoure-329473294-adfoduidr","2023-06-30T10:44:01.840866300","PENDING");
    	Reset rs3=new Reset(3,user1,"dowerwoure-329473294-adfoduidr","2023-06-30T10:44:01.840866300","PENDING");
    	
    	List<Reset> expectedResetList=new ArrayList<Reset>();
    	expectedResetList.add(rs2);
    	expectedResetList.add(rs3);
    	
    	when(resetrepo.findByStatusAndUser("PENDING",user1)).thenReturn(expectedResetList);
    	List<Reset> actualResetList=resetservice.getResetByStatusAndUser("PENDING", user1);
    	assertEquals(expectedResetList,actualResetList);
	}

}
