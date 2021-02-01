package com.sapient.learning.model;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = -4870061854652654067L;

	private String name;

	private Long phone;

	private String sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", phone=" + phone + ", sex=" + sex + "]";
	}

}
