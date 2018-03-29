package com.papi.utility;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.papi.entity.User;

public class PWUtility {

	// This Method returns set of email id of groups
	public List<String> getUserEmailIdOfGroups(List<Long> groups) {

		return new ArrayList<String>();
	}

	public Date formatSchedulerDate(String date) {

		return new Date(0);
	}

	public String generateRandomPasswordForUser(String userName) {

		return "Password";

	}

	public String generateUserName(String emailId) {

		return "userName";
	}

	public User createUserbyEmailId(String emailId) {

		return new User();
	}

}
