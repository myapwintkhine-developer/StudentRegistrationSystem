package com.ojt.studentmanagmentsb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.UserRepository;



@Service
public class UserService {
        @Autowired UserRepository userrepo;
    
      
        public boolean createUser(User user) {
        	User savedUser = userrepo.save(user);
            return savedUser != null;
        }
        
        public boolean updateUser(User user) {
        	User updatedUser=userrepo.save(user);
        	return updatedUser != null;
        }
        
        public List<User> getAllUsers(){
        	return userrepo.findAll();
        }
        
        public User getUserById(int id) {
        	return userrepo.findById(id).orElse(null);
        }
        
        
        public List<User> getUserByStatus(String status){
        	return userrepo.findByStatus(status);
        }
        
        public User getUserByUsername(String username) {
        	return userrepo.findByUsername(username);
        }
        
        public User getUserByIdAndName(int id,String username) {
        	return userrepo.findByIdAndUsername(id, username);
        }
        
        public List<User> searchUserByUsername(String keyword){
        	return userrepo.findByUsernameContaining(keyword);
        }
        
        public User getUserByNameEmail(String username, String email) {
			return userrepo.findByUsernameAndEmail(username, email);
		}
        
        public User getUserByEmailToken(String email_verify_token) {
        	return userrepo.findByEmailtoken(email_verify_token);
        }
        
        public User getUserByEmail(String email) {
        	return userrepo.findByEmail(email);
        }
		
        public List<User> getUserByEmailverifiedTrue(){
        	return userrepo.findByEmailverifiedTrue();
        }
        
        public User getUserByUsernameAndEmail(String username,String email) {
        	return userrepo.findByUsernameAndEmail(username, email);
        }
        
        public User searchUserByIdNameStatus(int id, String username,String status) {
        	return userrepo.findUserByIdAndUsernameAndStatus(id, username, status);
        }
        
        public User searchUserByIdName(int id,String username) {
        	return userrepo.findUserByIdAndUsername(id, username);
        }
        
        public User searchUserByIdStatus(int id,String status) {
        	return userrepo.findUserByIdAndStatus(id, status);
        }
        
        public List<User> searchUserByNameStatus(String username,String status){
        	return userrepo.findUserByUsernameAndStatus(username, status);
        }
        
        public List<User> searchUserByStatus(String status){
        	return userrepo.findUserByStatus(status);
        }
         
        
}
