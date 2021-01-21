package com.sapient.learning.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.apache.mesos.Protos;
import org.apache.mesos.Protos.ExecutorInfo;
import org.apache.mesos.Protos.Offer;
import org.apache.mesos.Protos.OfferID;
import org.apache.mesos.Protos.TaskInfo;
import org.apache.mesos.Scheduler;
import org.apache.mesos.SchedulerDriver;

public class MyScheduler implements Scheduler {

	private int launchedTasks = 0;
	private final ExecutorInfo executorInfo;

	public MyScheduler(ExecutorInfo executorInfo) {
		this.executorInfo = executorInfo;
	}

	@Override
	public void registered(SchedulerDriver schedulerDriver, Protos.FrameworkID frameworkID,
			Protos.MasterInfo masterInfo) {

	}

	@Override
	public void reregistered(SchedulerDriver schedulerDriver, Protos.MasterInfo masterInfo) {

	}

	@Override
	public void resourceOffers(SchedulerDriver schedulerDriver, List<Offer> list) {

		for (Offer offer : list) {
			List<TaskInfo> tasks = new ArrayList<TaskInfo>();
			Protos.TaskID taskId = Protos.TaskID.newBuilder().setValue(Integer.toString(launchedTasks++)).build();

			System.out.println("Launching printHelloWorld " + taskId.getValue() + " Hello World Java");
			TaskInfo printHelloWorld = TaskInfo.newBuilder().setName("printHelloWorld " + taskId.getValue())
					.setTaskId(taskId).setSlaveId(offer.getSlaveId())
					.addResources(Protos.Resource.newBuilder().setName("cpus").setType(Protos.Value.Type.SCALAR)
							.setScalar(Protos.Value.Scalar.newBuilder().setValue(1)))
					.addResources(Protos.Resource.newBuilder().setName("mem").setType(Protos.Value.Type.SCALAR)
							.setScalar(Protos.Value.Scalar.newBuilder().setValue(128)))
					.setExecutor(ExecutorInfo.newBuilder(executorInfo)).build();

			List<OfferID> offerIDS = new ArrayList<>();
			offerIDS.add(offer.getId());

			tasks.add(printHelloWorld);

			schedulerDriver.declineOffer(offer.getId());
			schedulerDriver.launchTasks(offerIDS, tasks);
		}

	}

	@Override
	public void offerRescinded(SchedulerDriver schedulerDriver, OfferID offerID) {

	}

	@Override
	public void statusUpdate(SchedulerDriver schedulerDriver, Protos.TaskStatus taskStatus) {

	}

	@Override
	public void frameworkMessage(SchedulerDriver schedulerDriver, Protos.ExecutorID executorID, Protos.SlaveID slaveID,
			byte[] bytes) {

	}

	@Override
	public void disconnected(SchedulerDriver schedulerDriver) {

	}

	@Override
	public void slaveLost(SchedulerDriver schedulerDriver, Protos.SlaveID slaveID) {

	}

	@Override
	public void executorLost(SchedulerDriver schedulerDriver, Protos.ExecutorID executorID, Protos.SlaveID slaveID,
			int i) {

	}

	@Override
	public void error(SchedulerDriver schedulerDriver, String s) {

	}
}