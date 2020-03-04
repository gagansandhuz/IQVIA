package com.iqvia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "schedule_data")
public class ScheduleData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date createdAt;
	private Date scheduleAt;
	private String message;
	private String jobStatus;

	public ScheduleData() {
	}

	public ScheduleData(Date createdAt, Date scheduleAt, String message, String jobStatus) {
		this.createdAt = createdAt;
		this.scheduleAt = scheduleAt;
		this.message = message;
		this.jobStatus = jobStatus;
	}
	
	

	public ScheduleData(String id, Date createdAt, Date scheduleAt, String message, String jobStatus) {
		this.id = id;
		this.createdAt = createdAt;
		this.scheduleAt = scheduleAt;
		this.message = message;
		this.jobStatus = jobStatus;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	@Column
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column
	public Date getScheduleAt() {
		return scheduleAt;
	}

	public void setScheduleAt(Date scheduleAt) {
		this.scheduleAt = scheduleAt;
	}

	@Column(columnDefinition = "TEXT")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Override
	public String toString() {
		return "ScheduleData [id=" + id + ", createdAt=" + createdAt + ", scheduleAt=" + scheduleAt + ", message="
				+ message + ", jobStatus=" + jobStatus + "]";
	}

}
