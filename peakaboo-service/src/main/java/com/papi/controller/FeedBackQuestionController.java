package com.papi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.papi.dao.FeedBackQuestionDao;
import com.papi.entity.FeedBackQuestion;
import com.papi.entity.Feedback;

/**
 * 
 * @author pshet
 *
 */
@RestController
@RequestMapping("/fbquestion")
public class FeedBackQuestionController {
	@Autowired
	FeedBackQuestionDao questionDao;

	@RequestMapping(value = "/getQuestionList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<FeedBackQuestion> getQuestionList() {
		System.out.println("GetQuestion");
		// questionDao.listQuestion();
		return questionDao.getAllFBQuestions();
	}

	@RequestMapping(value = "/getQuestion/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public FeedBackQuestion method7(@PathVariable("id") Long id) {

		return questionDao.getFBQuestion(id);
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public FeedBackQuestion getQuestion(@RequestBody FeedBackQuestion fbQuestion) {
		System.out.println("add question");
		questionDao.addFBQuestion(fbQuestion);
		System.out.println(fbQuestion);

		return fbQuestion;
	}

	@RequestMapping(value = "/addFeedback", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Feedback addFeedback(@RequestBody Feedback feedback) {
		System.out.println("add question");
		questionDao.addFeedback(feedback);
		System.out.println(feedback);

		return feedback;
	}
	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "Invalid URL : Please check Url";
	}
}
