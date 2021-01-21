package com.sapient.learning.events.user.commands;

import java.util.List;

import com.sapient.learning.events.base.Command;

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
