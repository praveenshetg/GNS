package com.papi.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.papi.dao.GroupDao;
import com.papi.dao.SettingsDao;
import com.papi.dao.UserDao;
import com.papi.entity.MailConfiguration;
import com.papi.entity.User;
import com.papi.utility.PWUtility;
import com.papi.wrapper.PasswordWrapper;
import com.papi.wrapper.UserWrapper;

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
	
	@Autowired
	SettingsDao settingsDao;

	@RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User method7(@PathVariable("id") Long id) {

		User user = new User();
		user = userDao.getUser(id);
		try {
//			FileOutputStream fos = new FileOutputStream("/home/ec2-user/img_avatar_output.png");
//			fos.write(user.getImage());
//			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> getUserList() {
		System.out.println("GetUser");

		return userDao.getAllUsers();
	}

	@RequestMapping(value = "/getUserList/{groupId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> getUserListForGroup(@PathVariable("groupId") Long groupId) {
		System.out.println("getUserListForGroup");

		return userDao.getAllUsersForGroup(groupId);
	}

	@RequestMapping(value = "/addUser/{groupId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HashMap<String, String> addUser(@RequestBody User user, @PathVariable("groupId") Long groupId) throws Exception {
		System.out.println("add user");
		HashMap<String, String> response =  new HashMap<String, String>();
		String username = PWUtility.generateUserName(user.getoEmail());
		String password = PWUtility.generateRandomPasswordForUser(username);
		user.setGroup_id(groupId);
		user.setUsername(username.toLowerCase());
		user.setPassword(password);
		byte[] bFile = new byte[100];
		user.setImage(bFile);
		try{
			user.setoEmail(user.getoEmail().toLowerCase());
			userDao.addUser(user);
			PWUtility.sendMail("Welcome Aboard",
					"<p>Hi " + user.getUsername()
							+ ",</p><p>A very warm welcome to Peakaboo! Thanks so much for making a commitment towards life long learning. You are on your way to super- growth, progress and beyond!<p><hr/><img src='peakaboo.proawitz.com/images/peakaboo_wall.jpg'/><hr/><p>Peakaboo helps you reinforce, retain and apply the learning learnt Everytime to take you a notch up.</p><p>We wish you super happiness and hyper success.</p><p>Happy learning! </p><p><a href='peakaboo.proawitz.com'>Click here </a>to start your progress right away.</p><p>Your Peakaboo account has been created successfully with your registered email id praveenshetg@gmail.com and the login details are as below</p><br/> username : "
							+ user.getoEmail() + "<br/> password : " + user.getPassword() + "</p>",
					user.getoEmail(), getmailConfigDegtails());
		}catch(java.sql.SQLIntegrityConstraintViolationException e){
			System.out.println("Duplicate entry user already Registerd with email:"+user.getoEmail());
			//e.printStackTrace();
			response.put("error", "true");
			response.put("msg","Duplicate entry, User already Registerd with email :"+user.getoEmail());
			return response;
		}
		response.put("error", "false");
		
		return response;
	}
	public MailConfiguration getmailConfigDegtails(){
		return settingsDao.getMailConfiguration(new Long(1));
	}
	@RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public User updateUser(@RequestBody User user, @PathVariable("userId") Long userId) throws Exception {
		User existingUserDetils = userDao.getUser(userId);
		user.setId(existingUserDetils.getId());
		user.setoEmail(existingUserDetils.getoEmail());
		user.setPassword(existingUserDetils.getPassword());
		user.setToken(existingUserDetils.getToken());
		user.setImage(existingUserDetils.getImage());
		user.setGroup_id(existingUserDetils.getGroup_id());
		user.setRole_name(existingUserDetils.getRole_name());
		userDao.updateUser(user);
		return user;
	}

	@RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HashMap<String, String> changePassword(@RequestBody PasswordWrapper passwordWrapper,
			@PathVariable("userId") Long id) throws Exception {
		HashMap<String, String> response = new HashMap<String, String>();
		User existingUserDetils = userDao.getUser(id);
		if (existingUserDetils.getPassword().equals(passwordWrapper.getOldPassword())) {
			existingUserDetils.setPassword(passwordWrapper.getNewPassword());
			userDao.updateUser(existingUserDetils);
			response.put("msg", "Password updated Successfully.");
		} else {
			response.put("msg", "Old password is not matching.");
		}
		return response;
	}

	@RequestMapping(value = "/createAutoUsers/{groupId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public HashMap<String, String> createAutoUsers(@RequestBody UserWrapper userWrapper, @PathVariable("groupId") Long groupId)
			throws Exception {
		System.out.println("createAutoUsers");
		HashMap<String, String> response = new HashMap<String, String>();
		List<User> userList = PWUtility.createUserFromUserEmail(userWrapper.getUsers(), groupId);
		try{
			for (User user : userList) {
				user.setoEmail(user.getoEmail().toLowerCase());
				user.setUsername(user.getUsername().toLowerCase());
				userDao.addUser(user);
				PWUtility.sendMail("Welcome Aboard",
						"<p>Hi "+ user.getUsername() +",</p><p>A very warm welcome to Peakaboo! Thanks so much for making a commitment towards life long learning. You are on your way to super- growth, progress and beyond!<p><img src='peakaboo.proawitz.com/images/peakaboo_wall.jpg' style='width:70%'/><p>Peakaboo helps you reinforce, retain and apply the learning learnt Everytime to take you a notch up.</p><p>We wish you super happiness and hyper success.</p><p>Happy learning! </p><p><a href='peakaboo.proawitz.com'>Click here </a>to start your progress right away.</p><p>Your Peakaboo account has been created successfully with your registered email id "+user.getoEmail()+" and the login details are as below</p><br/> username : "+user.getoEmail()+"<br/> password : "+user.getPassword()+"</p>",
						user.getoEmail(),getmailConfigDegtails());
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException e){
			response.put("error", "true");
			response.put("msg","Duplicate entry, User already Registerd with email :");
			return response;
		}
		response.put("error", "false");
		return response;

	}

	@RequestMapping(value = "/sss/{userId}", method = RequestMethod.POST, consumes = {
			"application/x-www-form-urlencoded", "multipart/form-data" })
	@ResponseBody
	public void addBlob(@PathVariable("userId") Long userId, @FormParam("file") InputStream uploadedInputStream)
			throws IOException {
		User user = userDao.getUser(userId);
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			user.setImage(out.toByteArray());
			userDao.updateUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean(name = "multipartResolver")
	@RequestMapping(value = "/upload/{userId}", method = RequestMethod.POST)
	public Boolean fileUpload(@PathVariable("userId") Long userId,@RequestParam("file") MultipartFile file) throws IOException {
		User user = userDao.getUser(userId);
		String originalFilename = file.getOriginalFilename();
		System.out.println(originalFilename);
		//String destinationPath ="/opt/apache-tomcat-8.5.30/webapps/peakaboo/media";
		//String destinationPath ="/Users/pshet/";
		// Save file on system
	if (!file.getOriginalFilename().isEmpty()) {
//			BufferedOutputStream outputStream = new BufferedOutputStream(
//					new FileOutputStream(new File(destinationPath, originalFilename)));
//
//			outputStream.write(file.getBytes());
//			outputStream.flush();
//			outputStream.close();
		   InputStream inputStream = file.getInputStream();
		   System.out.println(inputStream);
		   ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			user.setImage(out.toByteArray());
			userDao.updateUser(user);

			System.out.println(out.toByteArray());
			//user.setImage()
		} else {
			return false;
		}
		return true;
	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "Invalid URL : Please check Url";
	}
}
