package com.sapient.learning.events.base;

import java.util.Date;
import java.util.UUID;

import lombok.ToString;

@ToString
public abstract class Command {
	
	public final UUID id = UUID.randomUUID();
	
	public final Date created = new Date();
	
}
