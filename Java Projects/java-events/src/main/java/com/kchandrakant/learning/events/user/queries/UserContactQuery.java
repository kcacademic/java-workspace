package com.kchandrakant.learning.events.user.queries;

import com.kchandrakant.learning.events.base.Query;
import com.kchandrakant.learning.events.user.projections.UserNumberOfContacts;

public class UserContactQuery extends Query {

	private UserNumberOfContacts contacts;

	public UserContactQuery(UserNumberOfContacts contacts) {
		this.contacts = contacts;
	}

	public Integer getNumberOfContacts(String id) {
		return contacts.getContacts().get(id);
	}

}
