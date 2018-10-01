package com.papi.controller;

import java.util.List;

import org.apache.log4j.Logger;
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

/**
 * 
 * @author pshet
 *
 */
@RestController
@RequestMapping("/group")
public class GroupController {
	@Autowired
	GroupDao groupDao;
	@Autowired
	UserDao userDao;

	final static Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(value = "/getGroupList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Group> getGroupList() {
		logger.info("Inside Method : getGroupList()");
		// groupDao.listGroup();
		return groupDao.getAllGroups();
	}

	@RequestMapping(value = "/getGroup/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Group getGroup(@PathVariable("id") Long id) {
		logger.info("Inside Method : getGroup() with Id :"+id);
		Group group = groupDao.getGroup(id);
		group.setUser_count(userDao.getUserCountForGroup(id));
		return group;
	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Group createGroup(@RequestBody Group group) {
		logger.info("Inside Method : createGroup() with name :"+group.getName());
		groupDao.addGroup(group);
		logger.info("Group Created with name :"+group.getName());
		return group;
	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		logger.info("Invalid URL : Please check Url : Please check Url");
		return "Invalid URL : Please check Url";
	}
}
