package com.iqvia.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import com.iqvia.model.JobStatus;
import com.iqvia.model.ScheduleData;
import com.iqvia.service.SchedulerService;

public class SchedulerJobListener implements JobListener {

	private static final Logger log = LogManager.getLogger(SchedulerJobListener.class);

	public static final String LISTENER_NAME = "schedulerJobListener";

	private static SchedulerJobListener schedulerJobListener = null;

	private SchedulerJobListener() {

	}

	public static SchedulerJobListener getSingleton() {
		if (schedulerJobListener == null)
			schedulerJobListener = new SchedulerJobListener();

		return schedulerJobListener;
	}

	public String getName() {
		return LISTENER_NAME;
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		log.info("jobExecutionVetoed");
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		String jobName = context.getJobDetail().getKey().toString();
		log.info("Job: " + jobName + " jobToBeExecuted");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext jobContext, JobExecutionException jobException) {

		String jobName = jobContext.getJobDetail().getKey().toString();

		if (jobException != null) {
			log.error("jobWasExecuted Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
		}

		try {
			jobContext.getScheduler().deleteJob(jobContext.getJobDetail().getKey());
		} catch (SchedulerException e) {
			log.error("jobWasExecuted - Error: " + e.getMessage());
		}

		JobDetail jobDetail = jobContext.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		ScheduleData scheduleData = (ScheduleData) jobDataMap.get("scheduleData");
		SchedulerService schedulerService = (SchedulerService) jobDataMap.get("schedulerService");

		if (null != scheduleData) {
			if (scheduleData.getJobStatus().equalsIgnoreCase(JobStatus.CREATED.value)) {
				schedulerService.deleteJob(scheduleData.getId());
				schedulerService.addJob(scheduleData);
			}
		}

	}

}
