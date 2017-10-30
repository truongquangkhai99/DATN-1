package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 10/27/2017.
 */
@Entity
@Table(name = "questions")
public class Question {
	private Integer id;
	private String name;
	private String group;
	private List<Answer> answers = new ArrayList<>();

	public Question() {}

	public Question(String name, String group) {
		this.name = name;
		this.group = group;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Nationalized
	@Column(name = "name", unique = false, nullable = false, length = 1024)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "group_student", unique = false, nullable = false)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
