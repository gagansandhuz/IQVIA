package com.iqvia.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqvia.job.SchedulerJob;
import com.iqvia.job.SchedulerJobListener;
import com.iqvia.model.ScheduleData;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger log = LogManager.getLogger(SchedulerServiceImpl.class);

	@Autowired
	private ScheduleDataService scheduleDataService;

	private Scheduler scheduler = null;

	private void init() {
		try {
			if (scheduler == null) {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.getListenerManager().addJobListener(SchedulerJobListener.getSingleton());
			}
		} catch (SchedulerException e) {
			log.error("init - Error: " + e.getMessage());
		}
	}

	@PostConstruct
	public void startScheduler() {
		init();

		try {
			if (!scheduler.isStarted()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			log.error("startScheduler - Error: " + e.getMessage());
		}
	}

	@Override
	public void addJob(ScheduleData scheduleData) {
		try {

			init();
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("schedulerService", this);
			jobDataMap.put("scheduleDataService", scheduleDataService);
			jobDataMap.put("scheduleData", scheduleData);

			JobKey jobKey = new JobKey(scheduleData.getId(), "ScheduleDataJob");
			JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class).withIdentity(jobKey).usingJobData(jobDataMap)
					.build();

			Date runTime = scheduleData.getScheduleAt();

			TriggerKey triggerKey = new TriggerKey(scheduleData.getId(), "ScheduleDataTrigger");

			Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(triggerKey).startAt(runTime)
					.build();
			scheduler.scheduleJob(jobDetail, trigger);

		} catch (Exception e) {
			log.error("addJob - Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteJob(String id) {
		init();
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobKey jobKey = new JobKey(id, "ScheduleDataJob");
			if (scheduler.checkExists(jobKey))
				scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			log.error("deleteJob - Error: " + e.getMessage());
		}
	}

}
