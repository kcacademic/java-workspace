package com.kchandrakant.learning.events.user.aggregates;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kchandrakant.learning.events.base.Aggregate;
import com.kchandrakant.learning.events.base.Event;
import com.kchandrakant.learning.events.repository.EventStore;
import com.kchandrakant.learning.events.user.commands.CreateUserCommand;
import com.kchandrakant.learning.events.user.commands.UpdateUserCommand;
import com.kchandrakant.learning.events.user.events.AddContactEvent;
import com.kchandrakant.learning.events.user.events.AddNameEvent;
import com.kchandrakant.learning.events.user.events.DeleteContactEvent;
import com.kchandrakant.learning.events.user.events.GenerateUserIdEvent;
import com.kchandrakant.learning.events.user.events.UpdateNameEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAggregate extends Aggregate {

	private UUID id;
	private String userid;
	private String name;
	private List<String> contacts = new ArrayList<>();

	private EventStore repository;

	public UserAggregate(EventStore repository) {
		this.id = UUID.randomUUID();
		this.repository = repository;
	}

	public UserAggregate(EventStore repository, String id) {
		this.id = UUID.fromString(id);
		this.repository = repository;
		List<Event> events = repository.getEvents(id);
		for (Event event : events) {
			if (event instanceof GenerateUserIdEvent)
				apply((GenerateUserIdEvent) event);
			if (event instanceof AddNameEvent)
				apply((AddNameEvent) event);
			if (event instanceof UpdateNameEvent)
				apply((UpdateNameEvent) event);
			if (event instanceof AddContactEvent)
				apply((AddContactEvent) event);
			if (event instanceof DeleteContactEvent)
				apply((DeleteContactEvent) event);
		}
	}

	public void handle(CreateUserCommand command) {
		GenerateUserIdEvent generateUserIdEvent = new GenerateUserIdEvent();
		apply(generateUserIdEvent);
		repository.addEvent(generateUserIdEvent, id.toString());
		AddNameEvent addNameEvent = new AddNameEvent(userid, command.getName());
		apply(addNameEvent);
		repository.addEvent(addNameEvent, id.toString());
		for (String contact : command.getContacts()) {
			AddContactEvent addContactEvent = new AddContactEvent(userid,
					contact);
			apply(addContactEvent);
			repository.addEvent(addContactEvent, id.toString());
		}

	}

	public void handle(UpdateUserCommand command) {

		if (!this.name.equals(command.getName())) {
			UpdateNameEvent updateNameEvent = new UpdateNameEvent(userid,
					command.getName());
			apply(updateNameEvent);
			repository.addEvent(updateNameEvent, id.toString());
		}

		List<String> contactsToRemove = this.contacts.stream()
				.filter(c -> !command.getContacts().contains(c))
				.collect(Collectors.toList());
		for (String contact : contactsToRemove) {
			DeleteContactEvent deleteContactEvent = new DeleteContactEvent(
					userid, contact);
			apply(deleteContactEvent);
			repository.addEvent(deleteContactEvent, id.toString());
		}

		List<String> contactsToAdd = command.getContacts().stream()
				.filter(c -> !this.contacts.contains(c))
				.collect(Collectors.toList());
		for (String contact : contactsToAdd) {
			AddContactEvent addContactEvent = new AddContactEvent(userid,
					contact);
			apply(addContactEvent);
			repository.addEvent(addContactEvent, id.toString());
		}
	}

	public void apply(GenerateUserIdEvent event) {
		this.userid = event.getUserid();
	}

	public void apply(AddNameEvent event) {
		this.name = event.getName();
	}

	public void apply(UpdateNameEvent event) {
		this.name = event.getName();
	}

	public void apply(AddContactEvent event) {
		this.contacts.add(event.getContact());
	}

	public void apply(DeleteContactEvent event) {
		this.contacts.remove(event.getContact());
	}

}
