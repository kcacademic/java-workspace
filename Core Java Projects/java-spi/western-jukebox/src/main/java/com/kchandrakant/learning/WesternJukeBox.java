package com.kchandrakant.learning;

import java.util.Map;
import java.util.TreeMap;

public class WesternJukeBox implements JukeBox {

	private final Map<String, Song> songs;

	public WesternJukeBox() {
		songs = new TreeMap<>();
		Song song = new Song("Summer of '69", "Bryan Adams", "Bryan Adams", "1985");
		songs.put(song.getTitle(), song);
	}

	@Override
	public Song getSong(String title) {
		return songs.get(title);
	}

}
