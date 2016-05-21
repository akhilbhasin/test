package com.abc.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.model.GetMessagesRequest;
import com.abc.producer.Consumer;
import com.abc.producer.ConsumerProxy;
import com.abc.utils.JsonDataAdapter;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	private static Logger log = LoggerFactory.getLogger(ConsumerController.class);
	@Autowired
	private JsonDataAdapter dataAdapter;

	@Autowired
	private Consumer consumer;

	@RequestMapping("/getMessages")
	public List<String> getMessages(@RequestBody final String input) {
		try {
			log.warn("zzzzz input:" + input);
			GetMessagesRequest request = dataAdapter.read(input, GetMessagesRequest.class);
			ConsumerProxy proxy = new ConsumerProxy(request.getGroupId());
			List<String> messages = proxy.getMessages(request.getTopic());
			return messages;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("/scheduleConsumer")
	public void schedulerConsumer(@RequestBody final String input) {
		consumer.scheduleConsumer();
	}

}
