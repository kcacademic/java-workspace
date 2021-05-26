package com.kchandrakant.learning.events.user.projections;

import com.kchandrakant.learning.events.base.Projection;
import com.kchandrakant.learning.events.user.events.AddContactEvent;
import com.kchandrakant.learning.events.user.events.AddNameEvent;
import com.kchandrakant.learning.events.user.events.DeleteContactEvent;
import com.kchandrakant.learning.events.user.events.GenerateUserIdEvent;
import com.kchandrakant.learning.events.user.events.UpdateNameEvent;

public interface UserProjection extends Projection {

	void process(GenerateUserIdEvent event);
	void process(AddNameEvent event);
	void process(UpdateNameEvent event);
	void process(AddContactEvent event);
	void process(DeleteContactEvent event);

}
