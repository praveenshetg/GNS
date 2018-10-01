package com.papi.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.papi.entity.User;
import com.papi.wrapper.PasswordWrapper;

@Repository
public interface UserDao {

	public void addUser(User user) throws SQLIntegrityConstraintViolationException;

	public List<User> getAllUsers();
	public List<String> getAllUsersMailId(Long groupId);

	public List<User> getAllUsersForGroup(Long groupId);

	public void deleteUser(Long id);

	public User updateUser(User user);

	public User getUser(Long id);
	
	public User getUserByMailIdAndPassword(String mailId, String password);
	
	public User getUserByToken(String token);
	
	public int getUserCountForGroup(Long user_id);
	

}
