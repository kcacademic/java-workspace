package com.sapient.learning;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {

	public static void main(String[] args) {
	    Weld weld = new Weld();
	    WeldContainer container = weld.initialize();
	    ImageFileProcessor imageFileProcessor = container.select(ImageFileProcessor.class).get();
	  
	    System.out.println(imageFileProcessor.openFile("file1.png"));
	  
	    container.shutdown();
	}
}