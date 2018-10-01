package com.papi.dao;

import org.springframework.stereotype.Repository;

import com.papi.entity.Group;
import com.papi.entity.MailConfiguration;

public interface SettingsDao {
	public void updateMailConfiguration(MailConfiguration mailConfiguration);
	public MailConfiguration getMailConfiguration(Long id);
}
