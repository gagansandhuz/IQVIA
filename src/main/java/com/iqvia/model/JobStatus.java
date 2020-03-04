package com.iqvia.model;

public enum JobStatus {

	CREATED("created"), SUCCESS("success"), FAILED("failed");

	public final String value;

	private JobStatus(String value) {
		this.value = value;
	}
}
