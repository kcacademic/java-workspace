package com.kchandrakant.learning.events.user.events;

import com.kchandrakant.learning.events.base.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
final public class DeleteContactEvent extends Event {

	private final String userId;
	private final String contact;

}
