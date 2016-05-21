package com.abc.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.model.AddTopicRequest;
import com.abc.model.PublishToTopicRequest;
import com.abc.producer.ProducerProxy;
import com.abc.producer.ZKProxy;
import com.abc.producer.ZookeeperProxy;
import com.abc.utils.JsonDataAdapter;

@RestController
@RequestMapping("/producer")
public class ProducerController {
	private static Logger log = LoggerFactory.getLogger(ProducerController.class);

	@Autowired
	private JsonDataAdapter dataAdapter;
	@Autowired
	private ProducerProxy producerProxy;

	@RequestMapping("publishMessage")
	public @ResponseBody String publishToTopic(@RequestBody final String input) {
		log.warn("zzzz input:" + input);
		PublishToTopicRequest request = dataAdapter.read(input, PublishToTopicRequest.class);
		try {
			producerProxy.sendMessage(request.getTopic(), request.getMessage());
			return "Success";
		} catch (Exception e) {
			return "Failed";
		}

	}

	@RequestMapping("addTopic")
	public @ResponseBody String addTopic(@RequestBody final String input) {
		AddTopicRequest request = dataAdapter.read(input, AddTopicRequest.class);
		ZKProxy zk = new ZKProxy();
		zk.createTopic(request.getTopic(), 1, 1);
		return "Success";
	}

	@RequestMapping("getTopics")
	public @ResponseBody List<String> getTopics() {
		ZookeeperProxy zk = new ZookeeperProxy();
		return zk.getListOfTopics();
	}

}
