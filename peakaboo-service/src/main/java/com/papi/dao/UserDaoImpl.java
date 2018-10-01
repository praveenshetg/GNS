package com.papi.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.papi.entity.User;
import com.papi.wrapper.PasswordWrapper;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void addUser(User User) throws java.sql.SQLIntegrityConstraintViolationException {
		
	
		//try{
			
			
		sessionFactory.getCurrentSession().saveOrUpdate(User);
//		}catch(SQLIntegrityConstraintViolationException e){
//			
//		}

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsersForGroup(Long groupId) {

		return sessionFactory.getCurrentSession().createQuery("from User where group_id=" + groupId).list();
	}

	@Transactional
	@Override
	public void deleteUser(Long id) {
		User User = sessionFactory.getCurrentSession().load(User.class, id);
		if (null != User) {
			this.sessionFactory.getCurrentSession().delete(User);
		}

	}

	@Transactional
	public User getUser(Long id) {
		return  sessionFactory.getCurrentSession().get(User.class, id);
	}

	// @Transactional
	// public User getUserByMailId(String oEmail) {
	// return (User) sessionFactory.getCurrentSession().get(User.class, oEmail);
	// }
	@SuppressWarnings("unchecked")
	@Transactional
	public User getUserByMailIdAndPassword(String mailId, String password) {
		User user = null;
		Query<User> q = null;
		q = sessionFactory.getCurrentSession().createQuery("from User where oEmail like '" + mailId + "' and password like '" + password +"'");
		try {
			user = q.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		return user;

	}
	@SuppressWarnings("unchecked")
	@Transactional
	public User getUserByToken(String token) {
		User user = null;
		Query<User> q = null;
		q = sessionFactory.getCurrentSession().createQuery("from User where token like '" + token + "'");
		try {
			user =  q.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		return user;
	}

	@Override
	public User updateUser(User User) {
		sessionFactory.getCurrentSession().update(User);
		return User;
	}

	@Override
	public int getUserCountForGroup(Long groupId) {

		Integer count = (Integer) sessionFactory.getCurrentSession()
				.createQuery("select count(*)from User where group_id=" + groupId).uniqueResult();
		return count;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getAllUsersMailId(Long groupId) {
		return sessionFactory.getCurrentSession().createQuery("select oEmail from User where group_id=" + groupId).list();
	}
}
