package com.papi.dao;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.papi.entity.Group;
import com.papi.entity.Group;

@Repository
public interface GroupDao {

	public void addGroup(Group group);

	public List<Group> getAllGroups();

	public void deleteGroup(Long id);

	public Group updateGroup(Group group);

	public Group getGroup(Long id);
}
