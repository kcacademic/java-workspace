package com.kchandrakant.learning;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

public class MyService {

	private static MyService myService;
	private final ServiceLoader<JukeBox> loader;

	private MyService() {
		loader = ServiceLoader.load(JukeBox.class);
	}

	public static synchronized MyService getInstance() {
		if (myService == null) {
			myService = new MyService();
		}
		return myService;
	}

	public void refresh() {
		loader.reload();
	}

	public Optional<Song> getSong(String title) {
		Song song = null;
		Iterator<JukeBox> jukeBoxes = loader.iterator();
		while (song == null && jukeBoxes.hasNext()) {
			JukeBox jukeBox = jukeBoxes.next();
			song = jukeBox.getSong(title);
		}
		return Optional.ofNullable(song);
	}

}
