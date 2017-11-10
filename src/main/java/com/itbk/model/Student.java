package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	private Integer idT;
	private String idB;
	private String name;
	private String dateOfBirth;
	private String classStd;
	private String teacher;
	private Group group;
	private boolean isTested;
	private double score;
	private long timer;

	public Student() {
		this.isTested = false;
		this.score = 0;
		this.timer = 3600; //60 minute
	}

	public Student(Integer idT, String idB, String name, String dateOfBirth, String classStudent, String teacher) {
		this.idT = idT;
		this.idB = idB;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.classStd = classStudent;
		this.teacher = teacher;
		this.isTested = false;
		this.score = 0;
		this.timer = 3600; //60 minute
	}

	@Id
	@GeneratedValue
	@Column(name = "idt")
	public Integer getIdT() {
		return idT;
	}

	public void setIdT(Integer idT) {
		this.idT = idT;
	}

	@Column(name = "idb", unique = true, nullable = false)
	public String getIdB() {
		return idB;
	}

	public void setIdB(String idB) {
		this.idB = idB;
	}

	@Nationalized
	@Column(name = "name", unique = false, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "dob", unique = false, nullable = false)
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "class", unique = false, nullable = false)
	public String getClassStd() {
		return classStd;
	}

	public void setClassStd(String classStudent) {
		this.classStd = classStudent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Nationalized
	@Column(name = "teacher", unique = false, nullable = false)
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	@Column(name = "is_tested", unique = false, nullable = false)
	public boolean isTested() {
		return isTested;
	}

	public void setTested(boolean tested) {
		isTested = tested;
	}

	@Column(name = "score", columnDefinition = "NUMERIC(5,2)", unique = false, nullable = false)
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Column(name = "timer", unique = false, nullable = false)
	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}
}
