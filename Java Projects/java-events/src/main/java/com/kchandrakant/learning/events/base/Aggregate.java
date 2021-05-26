package com.kchandrakant.learning.events.base;

import java.util.Date;
import java.util.UUID;

import lombok.ToString;

@ToString
public abstract class Aggregate {
	
	public final Date created = new Date();
	
	public abstract UUID getId();
	
}
