package com.kchandrakant.learning.events.base;

import java.util.Date;
import java.util.UUID;

import lombok.ToString;

@ToString
public abstract class Query {
	
	public final UUID id = UUID.randomUUID();
	
	public final Date created = new Date();
	
}
