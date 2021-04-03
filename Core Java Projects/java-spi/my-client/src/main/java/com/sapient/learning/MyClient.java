package com.sapient.learning;

public class MyClient {
	public static void main(String[] args) {
		MyService myService = MyService.getInstance();
		requestSong("Rang De Basanti", myService);
		requestSong("Summer of '69", myService);
	}

	private static void requestSong(String songTitle, MyService myService) {
		myService.getSong(songTitle).ifPresentOrElse(
				song -> System.out.println("The song '" + songTitle + "' was found, here are the details:" + song),
				() -> System.out.println("The JukeBox doesn't have the song '" + songTitle + "' that you need."));
	}

}
