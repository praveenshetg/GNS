package com.papi.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.papi.entity.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void addUser(User User) {
		sessionFactory.getCurrentSession().saveOrUpdate(User);

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsersForGroup(Long groupId) {

		return sessionFactory.getCurrentSession().createQuery("from User where GROUP_ID=" + groupId).list();
	}

	@Transactional
	@Override
	public void deleteUser(Long id) {
		User User = (User) sessionFactory.getCurrentSession().load(User.class, id);
		if (null != User) {
			this.sessionFactory.getCurrentSession().delete(User);
		}

	}

	@Transactional
	public User getUser(Long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User updateUser(User User) {
		sessionFactory.getCurrentSession().update(User);
		return User;
	}

}
