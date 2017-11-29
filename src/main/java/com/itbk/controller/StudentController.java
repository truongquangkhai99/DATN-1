package com.itbk.controller;

import com.itbk.constant.Constant;
import com.itbk.dto.Examination;
import com.itbk.model.Answer;
import com.itbk.model.Question;
import com.itbk.service.GroupService;
import com.itbk.service.QuestionService;
import com.itbk.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by PC on 10/31/2017.
 */

@Controller
@RequestMapping(value = {"/student"})
public class StudentController {

	private ArrayList<Examination> examinations = new ArrayList<>();

	@Autowired
	private StudentService studentService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String studentTestGet(HttpServletRequest request, Model model) throws IOException {
		// set timer when user shutting down PC that not yet finished the test
		String userName = getUserName();
		if(userName != null) {
			Object isTested = studentService.findIsTestedByUsername(getUserName());
			if(isTested != null && !(boolean)isTested) {
				String timerStr = request.getParameter("timerLast");
				if(timerStr != null) {
					long timer = Long.parseLong(timerStr);
					studentService.updateTimer(getUserName(), timer);
					return "/student/test";
				}
			}
		}

		examinations.clear();
		String group = groupService.findGroupById((int)studentService.findGroupIdByUserName(getUserName())).getName();
		List<Question> arrayList = questionService.getExaminationByGroupId(groupService.findGroupByGroupName(group).getId());
		if(arrayList.isEmpty()) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_NOT_EXAM_FOR_STUDENT);
			return "/student/student";
		}
		LinkedList<Question> list = new LinkedList<>();
		list.addAll(arrayList);
		//start new
		Random random = new Random();
		int count = 0; // count number of questions
		for(int i = list.size(); i > 0; i--) {
			int randQuestion = random.nextInt(i);
			Question question = list.get(randQuestion);
			Examination examination = new Examination();
			examination.setQuestionId(question.getId());
			examination.setQuestion("Câu " + (++count) + ": " + question.getName());
			examination.setRadio(question.isRadio());
			ArrayList<Answer> arrayAnswer = new ArrayList<>();
			for(int j = question.getAnswers().size(); j > 0; j--) {
				int randAnswer = random.nextInt(j);
				arrayAnswer.add(question.getAnswers().get(randAnswer));
				question.getAnswers().remove(randAnswer);
			}
			examination.setAnswers(arrayAnswer);
			examinations.add(examination);
			list.remove(randQuestion);
		}
		//end new

		/*Map<Question, List<Answer>> map = new HashMap<>();
		for (Question a : list) {
			map.put(a, a.getAnswers());
		}

		Random rand = new Random();
		int count = 0;
		for(int i = map.size(); i > 0; i--) {
			Object[] questionList =  map.keySet().toArray();
			int ranQuestion = rand.nextInt(i);
			if(questionList[ranQuestion] instanceof Question) {
				Question question = (Question)questionList[ranQuestion];
				Examination examination = new Examination();
				examination.setQuestion("Câu " + (++count) + ": " + question.getName());
				ArrayList<Answer> stringArrayList = new ArrayList<>();
				for(int j = map.get(questionList[ranQuestion]).size(); j > 0; j--) {
					int ranAnswer = rand.nextInt(j);
					stringArrayList.add(map.get(questionList[ranQuestion]).get(ranAnswer));
					map.get(questionList[ranQuestion]).remove(ranAnswer);
				}
				examination.setAnswers(stringArrayList);
				examination.setRadio(question.isRadio());
				examination.setQuestionId(question.getId());
				map.remove(questionList[ranQuestion]);
				examinations.add(examination);
			}
		}*/

		model.addAttribute("examinations", examinations);
		if(userName != null) {
			model.addAttribute("isTested", studentService.findIsTestedByUsername(getUserName()));
			model.addAttribute("score", studentService.findStudentByUsername(getUserName()).getScore());
			model.addAttribute("timer", studentService.findTimerByUsername(getUserName()));
		}

		return "/student/test";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String studentTestPost(Model model, HttpServletRequest request) throws IOException {
		int numberOfQuestion = examinations.size();
		double pointPerQuestion = 10.0/numberOfQuestion;
		double score = 0.0;
		for(Examination examination : examinations) {
			if(examination.getRadio()) { // for radio button input
				String radioValue = request.getParameter("radio_" + examination.getQuestionId());
				if(radioValue != null) {
					for(Answer value : examination.getAnswers()) {
						if(radioValue.trim().equals(("radio_" + value.getAnswer()).trim()) && value.isExactly()) {
							score += pointPerQuestion;
						}
					}
				}
			} else { // for checkbox input
				int numOfAnswerRightOriginal = 0;
				int numOfAnswerRight = 0;
				int numOfAnswerWrong = 0;
				String []checkboxValue = request.getParameterValues("checkbox_" + examination.getQuestionId());
				if(checkboxValue != null) {
					for(Answer value : examination.getAnswers()) {
						if(value.isExactly()) numOfAnswerRightOriginal++;
					}
					for(int i = 0; i < checkboxValue.length; i++) {
						for(int j = 0; j < examination.getAnswers().size(); j++) {
							if(examination.getAnswers().get(j).isExactly() && checkboxValue[i].trim().equals(("checkbox_" + examination.getAnswers().get(j).getAnswer()).trim())) {
								numOfAnswerRight++;
								break;
							} else {
								if(j == examination.getAnswers().size() - 1) {
									numOfAnswerWrong++;
								}
							}
						}
					}
					score += ((numOfAnswerRight - numOfAnswerWrong) * 1.0 / numOfAnswerRightOriginal * pointPerQuestion);
				}
			}
		}
		if(score < 0) score = 0;
		studentService.updateScore(getUserName(), score);
		studentService.updateIsTested(getUserName(), true);
		studentService.updateTimer(getUserName(), 0);

		return "redirect:/student/test";
	}

	public String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = null;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		}

		return userName;
	}
}
