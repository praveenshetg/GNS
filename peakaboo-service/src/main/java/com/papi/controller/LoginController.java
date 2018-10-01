package com.papi.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.papi.dao.GroupDao;
import com.papi.dao.UserDao;
import com.papi.entity.Group;
import com.papi.entity.User;

@RestController
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	GroupDao groupDao;
	
	final static Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HashMap<String, HashMap<String,Object>> login(@RequestBody User user) {
		logger.info("Inside Method : login()");
		
		HashMap<String,HashMap<String,Object>> validUserDetails = new HashMap<String,HashMap<String,Object>>();
		HashMap<String,Object> error = new HashMap<String,Object>();
		
		User userExists = userDao.getUserByMailIdAndPassword(user.getoEmail().toLowerCase(), user.getPassword());
		
		if(userExists!=null){
			validUserDetails = getLoggedInUserDetails(user.getToken(), userExists, validUserDetails);
		}else{
			error.put("loginValid", "true");
			error.put("error", "true");
			error.put("error-type", "INVALID_USER");
			validUserDetails.put("error", error);
		}
		return validUserDetails;
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HashMap<String, HashMap<String,Object>> loginCheck(@RequestBody User user) {
		logger.info("Inside Method : loginCheck()");
		User userExists = userDao.getUserByToken(user.getToken());
		HashMap<String,HashMap<String,Object>> validUserDetails = new HashMap<String,HashMap<String,Object>>();
		HashMap<String,Object> error = new HashMap<String,Object>();
		if(userExists!=null && user.getToken().equals(userExists.getToken())){
			validUserDetails = getLoggedInUserDetails(user.getToken(), userExists, validUserDetails);
		}else{
			error.put("loginValid", "false");
			error.put("error", "true");
			error.put("error-type", "INVALID_TOKEN");
			validUserDetails.put("error", error);
		}
		return validUserDetails;
		//return userExists!=null && user.getToken().equals(userExists.getToken()) ? true : false;
	}
	
	public HashMap<String, HashMap<String,Object>> getLoggedInUserDetails(String token, User userExists, HashMap<String, HashMap<String,Object>> validUserDetails){

		Group group = groupDao.getGroup(userExists.getGroup_id());
		logger.info("User Exists : "+ userExists.getId());
		HashMap<String,Object> userDetail = new HashMap<String,Object>();
		HashMap<String,Object> groupDetail = new HashMap<String,Object>();
		HashMap<String,Object> error = new HashMap<String,Object>();
		
		
		userExists.setToken(token);
		userDao.updateUser(userExists);
	
		userDetail.put("id", userExists.getId().toString());
		userDetail.put("username", userExists.getUsername());
		userDetail.put("token", userExists.getToken());
		userDetail.put("dob", "");
		userDetail.put("role_name", userExists.getRole_name());
		userDetail.put("fName", userExists.getfName());
		userDetail.put("lName", userExists.getlName());
		userDetail.put("organization", userExists.getOrganization());
		userDetail.put("designation", userExists.getDesignation());
		userDetail.put("oEmail", userExists.getoEmail());
		userDetail.put("pEmail", userExists.getpEmail());
		userDetail.put("gender", userExists.getGender());
		userDetail.put("dob", userExists.getDob());
		userDetail.put("aboutYou", userExists.getAboutYou());
		userDetail.put("image", userExists.getImage());
		
		groupDetail.put("id", group.getId().toString());
		groupDetail.put("name", group.getName());
		groupDetail.put("category", group.getCategory());
		groupDetail.put("status", group.getStatus());
		//groupDetail.put("user_count", group.getUser_count().toString());
		groupDetail.put("user_count","0");

		validUserDetails.put("user", userDetail);
		validUserDetails.put("group", groupDetail);
		error.put("error", "false");
		validUserDetails.put("error", error);
		
	  return validUserDetails;
	}
}
