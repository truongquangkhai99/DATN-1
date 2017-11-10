package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by PC on 10/27/2017.
 */
@Entity
@Table(name = "questions")
public class Question {
	private Integer id;
	private String name;
	private boolean isRadio;
	private List<Answer> answers = new ArrayList<>();
	private Set<Group> groups;

	public Question() {}

	public Question(String name, boolean isRadio) {
		this.name = name;
		this.isRadio = isRadio;
	}
	@ManyToMany
	@JoinTable(
		name = "group_question",
		joinColumns = @JoinColumn(name = "question_id"),
		inverseJoinColumns = @JoinColumn(name = "group_id")
	)
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@Column(name = "is_radio", unique = false, nullable = false)
	public boolean isRadio() {
		return isRadio;
	}

	public void setRadio(boolean radio) {
		isRadio = radio;
	}
}
