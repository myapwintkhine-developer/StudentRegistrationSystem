package com.ojt.studentmanagmentsb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagmentsb.entity.UserView;
import com.ojt.studentmanagmentsb.repository.UserViewRepository;

@Service
public class UserViewService {
@Autowired UserViewRepository userviewrepo;

public List<UserView> getAllUsersFromView(){
	return userviewrepo.findAll();
}

public UserView searchById(int id) {
	Optional<UserView> opuserview=userviewrepo.findById(id);
	if (opuserview.isPresent()) {
        UserView userview = opuserview.get();
        return userview;
    } else {
        return null;
    }

}

public UserView searchUserByIdName(int id,String username) {
	return userviewrepo.findUserByIdAndUsername(id, username);
}

public UserView searchUserByIdStatus(int id,String status) {
	return userviewrepo.findUserByIdAndStatus(id, status);
}

public List<UserView> searchUserByUsername(String username) {
	return userviewrepo.findUserByUsername(username);
}

public List<UserView> searchUserByNameStatus(String username,String status){
	return userviewrepo.findUserByUsernameAndStatus(username, status);
}

public List<UserView> searchUserByStatus(String status){
	return userviewrepo.findUserByStatus(status);
}
public UserView searchUserByIdNameStatus(int id,String username,String status){
	return userviewrepo.findUserByIdAndUsernameAndStatus(id, username, status);
	}


}
