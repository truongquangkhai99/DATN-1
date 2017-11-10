package com.itbk.service;

import com.itbk.constant.Constant;
import com.itbk.model.Answer;
import com.itbk.model.Group;
import com.itbk.model.Question;
import com.itbk.model.Teacher;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PC on 11/9/2017.
 */

@Component
public class HandleFileWordService {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;

	public boolean readFileWord(MultipartFile file, Teacher teacher, Group group, String timer) {
		XWPFDocument document = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = (FileInputStream)(file.getInputStream());
			document = new XWPFDocument(fileInputStream);
			XWPFWordExtractor extractor = new XWPFWordExtractor(document);
			String content = extractor.getText();
			String [] arrayQuestion = content.split(Constant.SeparateTest.SEPARATE_QUESTION);
			readByGroup(arrayQuestion, teacher, group, timer);

			return true;
		} catch (Exception e) {
			System.out.println("exeception is: " + e.getMessage());
			return false;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void readByGroup(String [] arrayQuestion, Teacher teacher, Group group, String timer) {
		int numberOfQuestion = arrayQuestion.length;
		for(int i = 1; i < numberOfQuestion; i++) {
			String arrayResult[] = arrayQuestion[i].split(Constant.SeparateTest.SEPARATE_ANSWER);
			int countRightQuestion = 0;
			for(int k = 1; k < arrayResult.length; k++) {
				if(arrayResult[k].charAt(0) == '=') {
					countRightQuestion++;
				}
			}
			Question question = null;
			for(int j = 0; j < arrayResult.length; j++) {
				if(j == 0) {
					if(countRightQuestion >= 2) {
						question = new Question(arrayResult[j], false);
						Set<Group> groupSet = new HashSet<>();
						if(group != null) {
							groupSet.add(group);
						} else {
							groupSet.addAll(teacher.getGroups());
						}

						question.setGroups(groupSet);
						questionService.saveQuestion(question);
					} else {
						question = new Question(arrayResult[j], true);
						Set<Group> groupSet = new HashSet<>();
						if(group != null) {
							groupSet.add(group);
						} else {
							groupSet.addAll(teacher.getGroups());

						}
						question.setGroups(groupSet);
						questionService.saveQuestion(question);
					}
				} else {
					Answer answer = null;
					if(arrayResult[j].charAt(0) == '=') {
						answer = new Answer(arrayResult[j].substring(1),true);
						answer.setQuestion(question);
						answerService.saveAnswer(answer);
					} else {
						answer = new Answer(arrayResult[j].substring(1),false);
						answer.setQuestion(question);
						answerService.saveAnswer(answer);
					}
				}
			}
		}
		//set timer for student
		if(group != null) {
			studentService.updateTimerForGroupId(Long.parseLong(timer) * 60, groupService.findGroupByGroupName(group.getName()).getId());
		} else {
			for(Group groupNew : teacher.getGroups()) {
				studentService.updateTimerForGroupId(Long.parseLong(timer) * 60, groupService.findGroupByGroupName(groupNew.getName()).getId());
			}
		}
	}
}
