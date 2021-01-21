package com.sapient.learning.weaver;

import java.util.ArrayList;

public class MyList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	public boolean putIfAbsent(E o) {
		boolean absent = !super.contains(o);
		if (absent) {
			super.add(o);
		}
		return absent;
	}
}