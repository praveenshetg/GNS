package com.papi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.papi.entity.FeedBackQuestion;
import com.papi.entity.Feedback;

@Repository
public interface FeedBackQuestionDao {

	public void addFBQuestion(FeedBackQuestion fbQuestion);
	public void addFeedback(Feedback feedback);

	public List<FeedBackQuestion> getAllFBQuestions();

	public void deleteFBQuestion(Long id);

	public FeedBackQuestion updateFBQuestion(FeedBackQuestion fbQuestion);

	public FeedBackQuestion getFBQuestion(Long id);
}
