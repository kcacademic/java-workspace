package com.kchandrakant.learning.events.user;

import java.util.Arrays;
import java.util.List;

import com.kchandrakant.learning.events.repository.EventStore;
import com.kchandrakant.learning.events.user.aggregates.UserAggregate;
import com.kchandrakant.learning.events.user.commands.CreateUserCommand;
import com.kchandrakant.learning.events.user.commands.UpdateUserCommand;
import com.kchandrakant.learning.events.user.projections.UserNumberOfContacts;
import com.kchandrakant.learning.events.user.queries.UserContactQuery;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		EventStore repository = new EventStore();

		UserAggregate userAggregate = new UserAggregate(repository);
		List<String> contacts = Arrays.asList("9871164117", "9958522300");
		CreateUserCommand createUserCommand = new CreateUserCommand("Kumar",
				contacts);
		userAggregate.handle(createUserCommand);
		List<String> upadtedContacts = Arrays.asList("9871164118",
				"9958522300");
		UpdateUserCommand updateUserCommand = new UpdateUserCommand(
				"Kumar Chandrakant", upadtedContacts);
		userAggregate.handle(updateUserCommand);

		System.out.println(userAggregate);
		System.out.println((new UserAggregate(repository,
				userAggregate.getId().toString())));

		UserNumberOfContacts numberOfContacts = new UserNumberOfContacts(
				repository, userAggregate.getId().toString());
		UserContactQuery userContactQuery = new UserContactQuery(
				numberOfContacts);

		System.out.println(numberOfContacts);
		System.out.println(userContactQuery
				.getNumberOfContacts(userAggregate.getUserid()));
	}

}
