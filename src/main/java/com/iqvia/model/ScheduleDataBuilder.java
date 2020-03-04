package com.iqvia.model;

import java.util.Date;

public class ScheduleDataBuilder {

	private String message;
	private String deliveryTime;

	public ScheduleDataBuilder() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public ScheduleData getScheduleDataBuilder(Date scheduleAt) {
		return new ScheduleData(new Date(), scheduleAt, this.message, JobStatus.CREATED.value);
	}

	@Override
	public String toString() {
		return "ScheduleDataBuilder [message=" + message + ", deliveryTime=" + deliveryTime + "]";
	}

}
