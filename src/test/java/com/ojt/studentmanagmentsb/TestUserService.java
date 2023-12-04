package com.ojt.studentmanagmentsb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.UserRepository;
import com.ojt.studentmanagmentsb.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestUserService {

	@Mock
    private UserRepository userrepo;

    @InjectMocks
    private UserService userservice;

    @Test
    public void testCreateUser() {
        User sampleUser = new User();
        sampleUser.setUsername("Kaung");
        sampleUser.setEmail("kaung@gamil.com");
        sampleUser.setRole("USER");
        sampleUser.setStatus("INACTIVE");
        sampleUser.setRegisterDate("06-22-2023");
        sampleUser.setPassword("293483-342343-34237934-123");
        sampleUser.setEmail_expiration_date("2023-06-30T10:39:52.210591400");
        sampleUser.setEmailverified(false);
        sampleUser.setEmailtoken("60bc38b7-5172-49d6-a924-d6092d784288");

        when(userrepo.save(sampleUser)).thenReturn(sampleUser);

        boolean result = userservice.createUser(sampleUser);

        assertTrue(result);
    }
    
    @Test
    public void testGetAllUsers() {
    	User user1=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user2=new User(2,"aye","aye@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user3=new User(3,"su","su@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    
    	userservice.createUser(user1);
    	userservice.createUser(user2);
    	userservice.createUser(user3);
    	
    	List<User> expectedUsers =new ArrayList<User>();
    	expectedUsers.add(user1);
    	expectedUsers.add(user2);
    	expectedUsers.add(user3);
    	
    	when(userrepo.findAll()).thenReturn(expectedUsers);
    	List<User> actualUsers=userservice.getAllUsers();
    	
    	assertEquals(expectedUsers, actualUsers);

    }
    
    @Test
    public void testGetUserById() {
    	Optional<User> expectedUser=Optional.of(new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400"));
    	
    	when(userrepo.findById(1)).thenReturn(expectedUser);
    	User actualUser=userservice.getUserById(1);
    	assertEquals("mya",actualUser.getUsername());
    	assertEquals("mya@gmail.com",actualUser.getEmail());
    	assertEquals("06-22-2023",actualUser.getRegisterDate());
    	assertEquals("eouadfjdlfjadlfd-123",actualUser.getPassword());
    	assertEquals("USER",actualUser.getRole());
    	assertEquals("ACTIVE",actualUser.getStatus());
    	assertEquals(true,actualUser.isEmailverified());
    	assertEquals("3942343-dadfjdoru-234397439",actualUser.getEmailtoken());
    	assertEquals("2023-06-30T10:39:52.210591400",actualUser.getEmail_expiration_date());
    }
    
    @Test
    public void testGetUserByStatus() {
    	List<User> expectedList=new ArrayList<User> ();
    	User user1=new User(1,"mya","mya@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user2=new User(2,"aye","aye@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","ACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user3=new User(3,"su","su@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","INACTIVE",true,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	User user4=new User(4,"mi","mi@gmail.com","06-22-2023","eouadfjdlfjadlfd-123","USER","INACTIVE",false,"3942343-dadfjdoru-234397439","2023-06-30T10:39:52.210591400");
    	
    	expectedList.add(user3);
    	expectedList.add(user4);
    	
    	when(userrepo.findUserByStatus("n")).thenReturn(expectedList);
    	List<User> actualList=userservice.searchUserByStatus("n");
    	
    	assertEquals(expectedList,actualList);
    }
}
