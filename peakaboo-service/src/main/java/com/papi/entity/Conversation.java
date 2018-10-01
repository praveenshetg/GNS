package com.papi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@javax.persistence.Table(name = "PW_CHAT_HISTORY")
public class Conversation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "chat_id")
	Long id;
	String subject;
	@Type(type="text")
	String description;
	String period;
	String scheduleDate;
	String username;
	Long user_id;
	Long group_id;
	String type; // { message, event}
	String attachmentType; //{ video, image, pdf }
	String attachmentContentType; //{ image/jpg, image/png, video/mp4 }
	String status; // { requested, completed, failed }
	Boolean isAttachment = false;
	public Conversation(){
		
	}
	public Conversation(String description, Long user_id, Long group_id){
		this.subject = "";
		this.description = description ;
		this.period = "none";
		this.user_id = user_id;
		this.group_id = group_id;
		
	}
	
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getAttachmentContentType() {
		return attachmentContentType;
	}
	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}
	public Boolean getIsAttachment() {
		return isAttachment;
	}
	public void setIsAttachment(Boolean isAttachment) {
		this.isAttachment = isAttachment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	
}
