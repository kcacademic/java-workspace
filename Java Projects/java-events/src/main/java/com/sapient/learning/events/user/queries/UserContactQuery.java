package com.sapient.learning.events.user.queries;

import com.sapient.learning.events.base.Query;
import com.sapient.learning.events.user.projections.UserNumberOfContacts;

public class UserContactQuery extends Query {

	private UserNumberOfContacts contacts;

	public UserContactQuery(UserNumberOfContacts contacts) {
		this.contacts = contacts;
	}

	public Integer getNumberOfContacts(String id) {
		return contacts.getContacts().get(id);
	}

}
