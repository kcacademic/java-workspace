package com.sapient.learning.events.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.learning.events.base.Event;

public class EventStore {
	
	private static Map<String, List<Event>> store = new HashMap<>();
	
	public void addEvent(Event event, String id) {
		List<Event> events = store.get(id);
		if(events == null) {
			events = new ArrayList<Event>();
			events.add(event);
			store.put(id, events);
		} else {
			events.add(event);
		}
	}
	
	public List<Event> getEvents(String id) {
		return store.get(id);
	}

}
