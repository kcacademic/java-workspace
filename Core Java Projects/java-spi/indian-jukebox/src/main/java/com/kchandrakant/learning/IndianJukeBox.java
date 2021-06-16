package com.kchandrakant.learning;

import java.util.Map;
import java.util.TreeMap;

import com.kchandrakant.learning.JukeBox;
import com.kchandrakant.learning.Song;

public class IndianJukeBox implements JukeBox {

	private final Map<String, Song> songs;

	public IndianJukeBox() {
		songs = new TreeMap<>();
		Song song = new Song("Rang De Basanti", "A.R. Rehman", "Prasoon Joshi", "2005");
		songs.put(song.getTitle(), song);
	}

	@Override
	public Song getSong(String title) {
		return songs.get(title);
	}

}
