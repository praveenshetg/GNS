package com.papi.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.papi.entity.MailConfiguration;
@Repository
@Transactional
public class SettingsDaoImpl implements SettingsDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void updateMailConfiguration(MailConfiguration mailConfiguration) {
		sessionFactory.getCurrentSession().update(mailConfiguration);
	}

	@Transactional
	public MailConfiguration getMailConfiguration(Long id) {
		return sessionFactory.getCurrentSession().get(MailConfiguration.class, id);
	}

}
