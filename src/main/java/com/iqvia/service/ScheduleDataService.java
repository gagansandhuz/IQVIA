package com.iqvia.service;

import java.util.Date;
import java.util.List;

import com.iqvia.model.ScheduleData;

public interface ScheduleDataService {

	List<ScheduleData> findAll();

	ScheduleData findById(String id);

	ScheduleData save(ScheduleData scheduleData);

	List<ScheduleData> findByJobStatusAndCreatedAtBefore(String jobStatus, Date toDate);

	List<ScheduleData> findScheduleDataByJobStatus(String jobStatus);

}
