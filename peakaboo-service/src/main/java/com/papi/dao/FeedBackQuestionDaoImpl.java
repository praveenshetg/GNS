package com.papi.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.papi.entity.FeedBackQuestion;
import com.papi.entity.Feedback;

@Repository
public class FeedBackQuestionDaoImpl implements FeedBackQuestionDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void addFBQuestion(FeedBackQuestion feedBackQuestion) {
		sessionFactory.getCurrentSession().saveOrUpdate(feedBackQuestion);

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<FeedBackQuestion> getAllFBQuestions() {

		return sessionFactory.getCurrentSession().createQuery("from FeedBackQuestion").list();
	}

	@Transactional
	@Override
	public void deleteFBQuestion(Long id) {
		FeedBackQuestion fbQuestion =  sessionFactory.getCurrentSession().load(FeedBackQuestion.class, id);
		if (null != fbQuestion) {
			this.sessionFactory.getCurrentSession().delete(fbQuestion);
		}

	}

	@Transactional
	public FeedBackQuestion getFBQuestion(Long id) {
		return  sessionFactory.getCurrentSession().get(FeedBackQuestion.class, id);
	}

	@Transactional
	@Override
	public FeedBackQuestion updateFBQuestion(FeedBackQuestion fbQuestion) {
		sessionFactory.getCurrentSession().update(fbQuestion);
		return fbQuestion;
	}

	@Override
	public void addFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(feedback);

		
	}
}
