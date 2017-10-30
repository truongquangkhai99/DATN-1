package com.itbk.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

/**
 * Created by PC on 10/26/2017.
 */

@Entity
@Table(name = "teacher")
public class Teacher {

	private String name;
	private String account;
	private String password;
	private Integer id;

	public Teacher() {}

	public Teacher(String name, String account, String password) {
		this.account = account;
		this.name = name;
		this.password = password;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Nationalized
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "account", nullable = false)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
