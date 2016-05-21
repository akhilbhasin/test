package com.abc.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class GetMessagesRequest {

	private String topic;
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
