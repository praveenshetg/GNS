package com.papi.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.papi.dao.SettingsDao;
import com.papi.entity.MailConfiguration;
import com.papi.entity.User;

@RestController
@RequestMapping("/settings")
public class SettingsController {
	@Autowired
	SettingsDao settingsDao;
	final static Logger logger = Logger.getLogger(SettingsController.class);

	@RequestMapping(value = "/mailConfiguration", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Boolean updateMailConfiguration(@RequestBody MailConfiguration mailConfiguration) throws Exception {
		logger.info("updateMailConfiguration()");
		mailConfiguration.setId(new Long(1));
		settingsDao.updateMailConfiguration(mailConfiguration);
		return true;
	}
	
	@RequestMapping(value = "/fetchMailConfiguration", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public HashMap<String,String> fetchMailConfiguration() {
		logger.info("fetchMailConfiguration()");
		HashMap<String,String> responseMapper = new HashMap<String,String>();
		MailConfiguration mailConfiguration = settingsDao.getMailConfiguration(new Long(1));
		responseMapper.put("host", mailConfiguration.getHost());
		responseMapper.put("port", mailConfiguration.getPort());
		responseMapper.put("username", mailConfiguration.getUsername());
		responseMapper.put("password", mailConfiguration.getPassword());
		return responseMapper;
	}

}
