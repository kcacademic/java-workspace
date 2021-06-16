package com.kchandrakant.learning;

public class Song {

	private String title;
	private String composer;
	private String lyricist;
	private String year;

	public Song(String title, String composer, String lyricist, String year) {
		super();
		this.title = title;
		this.composer = composer;
		this.lyricist = lyricist;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getLyricist() {
		return lyricist;
	}

	public void setLyricist(String lyricist) {
		this.lyricist = lyricist;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Song [title=" + title + ", composer=" + composer + ", lyricist=" + lyricist + ", year=" + year + "]";
	}

}
