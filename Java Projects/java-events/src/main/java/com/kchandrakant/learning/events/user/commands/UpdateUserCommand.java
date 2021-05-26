package com.kchandrakant.learning.events.user.commands;

import java.util.List;

import com.kchandrakant.learning.events.base.Command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateUserCommand extends Command {

	private String name;
	private List<String> contacts;

}
