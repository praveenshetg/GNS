package com.papi.dao;

import java.util.List;

import com.papi.entity.Conversation;

public interface SchedulerDao {
	public void addConversation(Conversation conversation);

	public List<Conversation> getAllConversation();
	
	public List<Conversation> getAllConversationForGroup(Long groupId);
	

	public List<Conversation> getAllConversationForGroup(Long groupId, String type,String attachmentType, Boolean isAttachment);

	public void deleteConversation(Long id);

	public Conversation updateConversation(Conversation conversation);

	public Conversation getConversation(Long id);

}
