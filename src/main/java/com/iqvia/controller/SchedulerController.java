package com.iqvia.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iqvia.exception.BadRequestFoundException;
import com.iqvia.model.ScheduleData;
import com.iqvia.model.ScheduleDataBuilder;
import com.iqvia.service.ScheduleDataService;
import com.iqvia.service.SchedulerService;

@RestController
@RequestMapping("/api/scheduler/")
public class SchedulerController {

	@Autowired
	private ScheduleDataService scheduleDataService;
	
	@Autowired
	private SchedulerService schedulerService;

	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	@GetMapping
	public List<ScheduleData> findAllMessages() {
		return scheduleDataService.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ScheduleData saveMessage(@RequestBody ScheduleDataBuilder scheduleDataBuilder) {

		Date scheduleAt = validateScheduleData(scheduleDataBuilder);
		ScheduleData scheduleData = scheduleDataBuilder.getScheduleDataBuilder(scheduleAt);

		scheduleDataService.save(scheduleData);
		schedulerService.addJob(scheduleData);
		
		return scheduleData;
	}

	private Date validateScheduleData(ScheduleDataBuilder scheduleDataBuilder) {
		if (null == scheduleDataBuilder.getMessage() || scheduleDataBuilder.getMessage().isEmpty()) {
			throw new BadRequestFoundException("Please enter some value for message.");
		}
		if (null == scheduleDataBuilder.getDeliveryTime() || scheduleDataBuilder.getDeliveryTime().isEmpty()) {
			throw new BadRequestFoundException("Please enter delivery time.");
		}

		try {
			Date scheduleAt = formatter.parse(scheduleDataBuilder.getDeliveryTime());
			Date nowDate = new Date();
			long secondsLeft = (scheduleAt.getTime() - nowDate.getTime()) / 1000;

			if (secondsLeft <= 0) {
				throw new BadRequestFoundException(
						"Invalid delivery time provided to schedule your message, please enter a new date and try again later.");
			}

			return scheduleAt;

		} catch (ParseException parseException) {
			throw new BadRequestFoundException(
					"Invalid value provided for Date, please use this format to enter date: MM-dd-yyyy HH:mm:ss");
		}

	}

}
