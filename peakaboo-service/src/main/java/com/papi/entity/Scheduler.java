package com.papi.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Scheduler implements Serializable {
	@Id
	@GeneratedValue(generator = "increment")
	// @GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id")
	Long id;
	String subject;
	String description;
	String period;
	Date scheduleDate;
	List<Long> teams;

}
