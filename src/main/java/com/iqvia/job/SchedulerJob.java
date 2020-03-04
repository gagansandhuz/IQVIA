package com.iqvia.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.iqvia.model.JobStatus;
import com.iqvia.model.ScheduleData;
import com.iqvia.service.ScheduleDataService;

public class SchedulerJob implements Job {

	private static final Logger log = LogManager.getLogger(SchedulerJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		ScheduleData scheduleData = (ScheduleData) jobDataMap.get("scheduleData");
		ScheduleDataService scheduleDataService = (ScheduleDataService) jobDataMap.get("scheduleDataService");

		log.info("\n\n" + scheduleData.getMessage() + "\n\n");

		scheduleData.setJobStatus(JobStatus.SUCCESS.value);
		scheduleDataService.save(scheduleData);

	}

}
