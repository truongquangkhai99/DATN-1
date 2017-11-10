package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by PC on 11/9/2017.
 */

@Entity
@Table(name = "groups")
public class Group {

	private Integer id;
	private String name;
	private Teacher teacher;
	private Set<Student> students;

	public Group() {}

	public Group(String name) {
		this.name = name;
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
	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
