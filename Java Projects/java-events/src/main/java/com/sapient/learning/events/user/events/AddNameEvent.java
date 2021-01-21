package com.sapient.learning.events.user.events;

import com.sapient.learning.events.base.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
final public class AddNameEvent extends Event {

	private final String userId;
	private final String name;

}
