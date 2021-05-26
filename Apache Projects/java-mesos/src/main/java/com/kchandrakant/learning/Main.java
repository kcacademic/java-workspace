package com.kchandrakant.learning;

import org.apache.mesos.MesosSchedulerDriver;
import org.apache.mesos.Protos;
import org.apache.mesos.Protos.CommandInfo;
import org.apache.mesos.Protos.ExecutorInfo;
import org.apache.mesos.Protos.FrameworkInfo;

import com.kchandrakant.learning.scheduler.MyScheduler;

public class Main {

	public static void main(String[] args) {

		String path = System.getProperty("user.dir") + "/target/java-mesos-0.0.1-SNAPSHOT.jar";

		CommandInfo.URI uri = CommandInfo.URI.newBuilder().setValue(path).setExtract(false).build();

		String helloWorldCommand = "java -cp java-mesos-0.0.1-SNAPSHOT.jar com.kchandrakant.learning.executor.MyExecutor";
		
		CommandInfo commandInfoHelloWorld = CommandInfo.newBuilder().setValue(helloWorldCommand).addUris(uri).build();

		ExecutorInfo executorHelloWorld = ExecutorInfo.newBuilder()
				.setExecutorId(Protos.ExecutorID.newBuilder().setValue("MyExecutor"))
				.setCommand(commandInfoHelloWorld).setName("Hello World (Java)").setSource("java").build();

		FrameworkInfo.Builder frameworkBuilder = FrameworkInfo.newBuilder().setFailoverTimeout(120000).setUser("")
				.setName("Hello World Framework (Java)");

		frameworkBuilder.setPrincipal("test-framework-java");

		MesosSchedulerDriver driver = new MesosSchedulerDriver(new MyScheduler(executorHelloWorld),
				frameworkBuilder.build(), args[0]);

		int status = driver.run() == Protos.Status.DRIVER_STOPPED ? 0 : 1;

		// Ensure that the driver process terminates.
		driver.stop();

		System.exit(status);
	}

}
