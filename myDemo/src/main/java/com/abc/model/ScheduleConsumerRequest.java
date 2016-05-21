package com.abc.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ScheduleConsumerRequest {

	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
