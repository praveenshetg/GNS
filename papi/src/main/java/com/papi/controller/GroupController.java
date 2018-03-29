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

	@RequestMapping(value = "/getGroupList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Group> getGroupList() {
		System.out.println("GetGroup");
		// groupDao.listGroup();
		return groupDao.getAllGroups();
	}

	@RequestMapping(value = "/getGroup/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Group method7(@PathVariable("id") Long id) {

		return groupDao.getGroup(id);
	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Group getGroup(@RequestBody Group group) {
		System.out.println("add group");
		groupDao.addGroup(group);
		System.out.println(group);

		return group;
	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "Invalid URL : Please check Url";
	}
}
