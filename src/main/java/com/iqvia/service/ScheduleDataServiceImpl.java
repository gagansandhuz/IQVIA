package com.iqvia.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqvia.model.ScheduleData;
import com.iqvia.repository.ScheduleDataRepository;

@Service
public class ScheduleDataServiceImpl implements ScheduleDataService {

	@Autowired
	private ScheduleDataRepository scheduleDataRepository;

	@Override
	public List<ScheduleData> findAll() {
		return (List<ScheduleData>) scheduleDataRepository.findAll();
	}

	@Override
	public ScheduleData findById(String id) {
		return scheduleDataRepository.findById(id).get();
	}

	@Override
	public ScheduleData save(ScheduleData scheduleData) {
		return scheduleDataRepository.save(scheduleData);
	}

	@Override
	public List<ScheduleData> findByJobStatusAndCreatedAtBefore(String jobStatus, Date toDate) {
		return scheduleDataRepository.findByJobStatusAndCreatedAtBefore(jobStatus, toDate);
	}

	@Override
	public List<ScheduleData> findScheduleDataByJobStatus(String jobStatus) {
		return scheduleDataRepository.findScheduleDataByJobStatus(jobStatus);
	}

}
