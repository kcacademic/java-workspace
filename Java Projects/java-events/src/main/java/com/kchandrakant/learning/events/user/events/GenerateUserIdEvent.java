package com.kchandrakant.learning.events.user.events;

import java.util.UUID;

import com.kchandrakant.learning.events.base.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
final public class GenerateUserIdEvent extends Event {

	private final String userid = UUID.randomUUID().toString();

}
