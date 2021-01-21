package com.sapient.learning.events.user.projections;

import java.util.ArrayList;
import java.util.List;

import com.sapient.learning.events.user.events.AddContactEvent;
import com.sapient.learning.events.user.events.AddNameEvent;
import com.sapient.learning.events.user.events.GenerateUserIdEvent;
import com.sapient.learning.events.user.events.DeleteContactEvent;
import com.sapient.learning.events.user.events.UpdateNameEvent;

import lombok.Data;

@Data
public class UserSummary implements UserProjection {
	
	private String id;
	private String name;
	private List<String> contacts = new ArrayList<>();
	
	public void process(GenerateUserIdEvent event) {
		this.id = event.getUserid();
	}
	
	public void process(AddNameEvent event) {
		this.name = event.getName();
	}
	
	public void process(UpdateNameEvent event) {
		this.name = event.getName();
	}
	
	public void process(AddContactEvent event) {
		this.contacts.add(event.getContact());
	}
	
	public void process(DeleteContactEvent event) {
		this.contacts.remove(event.getContact());
	}

}
