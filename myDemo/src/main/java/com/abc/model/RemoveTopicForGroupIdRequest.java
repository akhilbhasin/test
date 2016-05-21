package com.abc.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class RemoveTopicForGroupIdRequest {

	private String groupId;
	private String topic;

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
