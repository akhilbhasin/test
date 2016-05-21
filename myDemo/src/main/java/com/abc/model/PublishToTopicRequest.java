package com.abc.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PublishToTopicRequest {
	private String topic;
	private String message;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
