package com.papi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.papi.entity.User;

@RestController
@RequestMapping("/")
public class HomeController {
	@RequestMapping()
	public String welcomePage() {
		return "Welcome to Duolingo.";
	}
	
	@RequestMapping(value = "/resetPassword/{email-id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void getUser(@RequestBody User user, @PathVariable("email-id") Long groupId) throws Exception {
		System.out.println("Reset Password");

		//user.setGroup(groupDao.getGroup(groupId));
		//System.out.println(user);
		//userDao.addUser(user);

	}
}
