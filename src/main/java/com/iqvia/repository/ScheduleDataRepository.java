package com.iqvia.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iqvia.model.ScheduleData;

@Repository
public interface ScheduleDataRepository extends CrudRepository<ScheduleData, String> {

	List<ScheduleData> findByJobStatusAndCreatedAtBefore(String jobStatus, Date toDate);

	List<ScheduleData> findScheduleDataByJobStatus(String jobStatus);

}
