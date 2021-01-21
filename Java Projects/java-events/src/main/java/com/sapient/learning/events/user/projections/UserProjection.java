package com.sapient.learning.events.user.projections;

import com.sapient.learning.events.base.Projection;
import com.sapient.learning.events.user.events.AddContactEvent;
import com.sapient.learning.events.user.events.AddNameEvent;
import com.sapient.learning.events.user.events.GenerateUserIdEvent;
import com.sapient.learning.events.user.events.DeleteContactEvent;
import com.sapient.learning.events.user.events.UpdateNameEvent;

public interface UserProjection extends Projection {

	void process(GenerateUserIdEvent event);
	void process(AddNameEvent event);
	void process(UpdateNameEvent event);
	void process(AddContactEvent event);
	void process(DeleteContactEvent event);

}
