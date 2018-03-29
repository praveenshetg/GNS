package com.papi.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.papi.entity.Group;

@Repository
public class GroupDaoImpl implements GroupDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void addGroup(Group Group) {
		sessionFactory.getCurrentSession().saveOrUpdate(Group);

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups() {

		return sessionFactory.getCurrentSession().createQuery("from Group").list();
	}

	@Transactional
	@Override
	public void deleteGroup(Long id) {
		Group Group = (Group) sessionFactory.getCurrentSession().load(Group.class, id);
		if (null != Group) {
			this.sessionFactory.getCurrentSession().delete(Group);
		}

	}

	@Transactional
	public Group getGroup(Long id) {
		return (Group) sessionFactory.getCurrentSession().get(Group.class, id);
	}

	@Transactional
	@Override
	public Group updateGroup(Group Group) {
		sessionFactory.getCurrentSession().update(Group);
		return Group;
	}

}
