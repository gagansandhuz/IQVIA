package com.iqvia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.iqvia.model.JobStatus;
import com.iqvia.model.ScheduleData;
import com.iqvia.service.ScheduleDataService;
import com.iqvia.service.SchedulerService;

@Component
public class PresetDataLoader implements ApplicationRunner {

	@Autowired
	private ScheduleDataService scheduleDataService;

	@Autowired
	private SchedulerService schedulerService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// Schedule all the created Jobs at the time of starting up the system
		for (ScheduleData scheduleData : scheduleDataService.findScheduleDataByJobStatus(JobStatus.CREATED.value)) {
			schedulerService.addJob(scheduleData);
		}

	}

}
