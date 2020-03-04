package com.iqvia.service;

import com.iqvia.model.ScheduleData;

public interface SchedulerService {

	void addJob(ScheduleData scheduleData);

	void deleteJob(String id);

}
