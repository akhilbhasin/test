package com.abc.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AddTopicRequest {
	
	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
