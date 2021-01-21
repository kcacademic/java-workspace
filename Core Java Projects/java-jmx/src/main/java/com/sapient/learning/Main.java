package com.sapient.learning;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sapient.learning.mbeans.Game;

public class Main {

	public static void main(String[] args) {

		try {
			ObjectName objectName = new ObjectName("com.sapient.learning:type=basic,name=game");
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			server.registerMBean(new Game(), objectName);
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException
				| NotCompliantMBeanException e) {
			e.printStackTrace();
		}

		while (true) {
		}

	}

}