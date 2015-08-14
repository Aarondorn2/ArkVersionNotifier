package com.hackeraj.arkversionnotifier.notificationjob;

import java.text.SimpleDateFormat;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

public class NotificationJobRunner {
private static Scheduler scheduler = null;


public static void start() {
	try {
		if (scheduler == null) {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			System.out.println("built scheduler");
		}
		if (!scheduler.isStarted()) {
			scheduler.start();
			System.out.println("started scheduler");
		}
		
		scheduleJob(60, false);
		System.out.println("scheduled job");
	
	} catch (SchedulerException se) {
		se.printStackTrace();
	}
}

public static void stop() {
	try {
		if (scheduler != null && !scheduler.isShutdown()) {
		    scheduler.shutdown();
		    scheduler = null;
			System.out.println("stopped scheduler");
		}
	} catch (SchedulerException e) {
		e.printStackTrace();
	}
}


public static void scheduleJob(int time, boolean overwrite) {

	try {
		if(overwrite) {
			System.out.println("overwriting previous job");
			scheduler.unscheduleJob(new TriggerKey("ABTrigger", "group1"));
		}
	 
		Trigger arkBarTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity("ABTrigger", "group1")
					.withSchedule(
					    SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(time).repeatForever())
					.build();

		System.out.println("built trigger");

		JobDetail arkBarJob = JobBuilder.newJob(NotificationJob.class)
			.withIdentity("ABJob", "group1").build();

		System.out.println("built job");
		
		scheduler.scheduleJob(arkBarJob, arkBarTrigger);
		System.out.println("scheduled job with trigger");
	} catch (SchedulerException e) {
		e.printStackTrace();
	}
}

public static String checkStatus() {
	String status = "Not Running any jobs";
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		
	if (scheduler != null) {
		try {
			// enumerate each job group
			for(String group: scheduler.getJobGroupNames()) {
			    // enumerate each job in group
			    for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {

			    	//get triggers
			  	  	@SuppressWarnings("unchecked")
			  	  	List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
			  	  
		    		status = "\r\n" +
						"Currently Running job: " +
						jobKey.getName();
		    		status = status + "\r\n" +
						"Job last executed: " +
						sdf.format(triggers.get(0).getPreviousFireTime());
		    		status = status + "\r\n" +
						"Job will execut again at: " +
						sdf.format(triggers.get(0).getNextFireTime());
			    }
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	return status;
}

}