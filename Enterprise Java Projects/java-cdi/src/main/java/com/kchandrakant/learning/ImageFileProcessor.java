package com.kchandrakant.learning;

import javax.inject.Inject;

public class ImageFileProcessor {

	@Inject
	private ImageFileEditor imageFileEditor;

	public String openFile(String fileName) {
		return imageFileEditor.openFile(fileName);
	}

	public String editFile(String fileName) {
		return imageFileEditor.editFile(fileName);
	}

	public String writeFile(String fileName) {
		return imageFileEditor.writeFile(fileName);
	}

	public String saveFile(String fileName) {
		return imageFileEditor.saveFile(fileName);
	}

}