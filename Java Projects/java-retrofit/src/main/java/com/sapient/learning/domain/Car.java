package com.sapient.learning.domain;

import java.util.Date;

public class Car {
	private String id;
	private String name;
	private Date releaseDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + "]";
	}

}
