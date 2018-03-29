package com.papi.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.papi.entity.User;

@Repository
public interface UserDao {

	public void addUser(User user);

	public List<User> getAllUsers();

	public List<User> getAllUsersForGroup(Long groupId);

	public void deleteUser(Long id);

	public User updateUser(User user);

	public User getUser(Long id);

}
