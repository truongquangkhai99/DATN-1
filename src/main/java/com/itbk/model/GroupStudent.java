package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class GroupStudent {
	private Integer ordinal;
	private String id;
	private String name;
	private String dateOfBirth;
	private String classStudent;
	private String teacher;
	private String group;

	public GroupStudent() {}

	public GroupStudent(Integer ordinal, String id, String name, String dateOfBirth, String classStudent, String teacher, String group) {
		this.ordinal = ordinal;
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.classStudent = classStudent;
		this.teacher = teacher;
		this.group = group;
	}

	@Id
	@GeneratedValue
	@Column(name = "ordinal")
	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Nationalized
	@Column(name = "name", unique = false, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "date_of_birth", unique = false, nullable = false)
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "class", unique = false, nullable = false)
	public String getClassStudent() {
		return classStudent;
	}

	public void setClassStudent(String classStudent) {
		this.classStudent = classStudent;
	}

	@Nationalized
	@Column(name = "teacher", unique = false, nullable = false)
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	@Column(name = "group_student", unique = false, nullable = false)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
