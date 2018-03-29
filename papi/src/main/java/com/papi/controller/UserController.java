package com.papi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.papi.dao.GroupDao;
import com.papi.dao.UserDao;
import com.papi.entity.Group;
import com.papi.entity.User;

/**
 * 
 * @author pshet
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserDao userDao;
	@Autowired
	GroupDao groupDao;

	@RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User method7(@PathVariable("id") Long id) {

		return userDao.getUser(id);
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> getUserList() {
		System.out.println("GetUser");

		return userDao.getAllUsers();
	}

	@RequestMapping(value = "/getUserList{groupId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> getUserListForGroup(@PathVariable("groupId") Long groupId) {
		System.out.println("getUserListForGroup");

		return userDao.getAllUsersForGroup(groupId);
	}

	@RequestMapping(value = "/addUser/{groupId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void getUser(@RequestBody User user, @PathVariable("groupId") Long groupId) throws Exception {
		System.out.println("add user");

		user.setGroup(groupDao.getGroup(groupId));
		System.out.println(user);
		userDao.addUser(user);

	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "Invalid URL : Please check Url";
	}
}
