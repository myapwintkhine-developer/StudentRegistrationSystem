package com.ojt.studentmanagmentsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.Reset;
import com.ojt.studentmanagmentsb.entity.User;
import com.ojt.studentmanagmentsb.repository.ResetRepository;


@Service
public class ResetService {
	@Autowired ResetRepository resetrepo;
		
		public Reset getResetByResetToken(String reset_token) {
			return resetrepo.findByResettoken(reset_token);
		}
		
		public boolean createReset(Reset reset) {
			Reset savedReset=resetrepo.save(reset);
			return savedReset != null;
		}
		
		public Reset getResetByUser(User user) {
			return resetrepo.findByUser(user);
		}
		
		public List<Reset> getResetByStatusAndUser(String status,User user){
			return resetrepo.findByStatusAndUser(status, user);
		}
		
}
