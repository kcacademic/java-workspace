package com.sapient.learning;

import java.io.IOException;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sapient.learning.job.HelloJob;

public class Main {

	public static void main(String[] args) throws IOException {

		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();

			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder
					.newJob(HelloJob.class)
					.withIdentity("myJob", "myGroup")
					.build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("myTrigger", "myGroup")
					.startNow()
					.withSchedule(
							CronScheduleBuilder
							.cronSchedule("0/5 * * * * ?"))
					.build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

			//scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
