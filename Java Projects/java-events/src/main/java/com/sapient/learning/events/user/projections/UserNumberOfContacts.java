package com.sapient.learning.events.user.projections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.learning.events.base.Event;
import com.sapient.learning.events.repository.EventStore;
import com.sapient.learning.events.user.events.AddContactEvent;
import com.sapient.learning.events.user.events.AddNameEvent;
import com.sapient.learning.events.user.events.DeleteContactEvent;
import com.sapient.learning.events.user.events.GenerateUserIdEvent;
import com.sapient.learning.events.user.events.UpdateNameEvent;

import lombok.Data;

@Data
public class UserNumberOfContacts implements UserProjection {

	private Map<String, Integer> contacts = new HashMap<>();

	public UserNumberOfContacts(EventStore repository, String id) {

		List<Event> events = repository.getEvents(id);
		for (Event event : events) {
			if (event instanceof GenerateUserIdEvent)
				process((GenerateUserIdEvent) event);
			if (event instanceof AddNameEvent)
				process((AddNameEvent) event);
			if (event instanceof UpdateNameEvent)
				process((UpdateNameEvent) event);
			if (event instanceof AddContactEvent)
				process((AddContactEvent) event);
			if (event instanceof DeleteContactEvent)
				process((DeleteContactEvent) event);
		}
	}

	public void process(GenerateUserIdEvent event) {
		if (!contacts.containsKey(event.getUserid())) {
			contacts.put(event.getUserid(), 0);
		}
	}

	public void process(AddNameEvent event) {

	}

	public void process(UpdateNameEvent event) {

	}

	public void process(AddContactEvent event) {
		if (contacts.containsKey(event.getUserId())) {
			int c = contacts.get(event.getUserId()).intValue() + 1;
			contacts.put(event.getUserId(), new Integer(c));
		}
	}

	public void process(DeleteContactEvent event) {
		if (contacts.containsKey(event.getUserId())) {
			int c = contacts.get(event.getUserId()).intValue() - 1;
			contacts.put(event.getUserId(), new Integer(c));
		}
	}

}
