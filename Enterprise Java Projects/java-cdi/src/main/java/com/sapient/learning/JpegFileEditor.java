package com.sapient.learning;

import javax.enterprise.inject.Alternative;

@Alternative
public class JpegFileEditor implements ImageFileEditor {

	@Override
	public String openFile(String fileName) {
		return "Opening JPEG file " + fileName;
	}

	@Override
	public String editFile(String fileName) {
		return "Editing JPEG file " + fileName;
	}

	@Override
	public String writeFile(String fileName) {
		return "Writing JPEG file " + fileName;
	}

	@Override
	public String saveFile(String fileName) {
		return "Saving JPEG file " + fileName;
	}
}
