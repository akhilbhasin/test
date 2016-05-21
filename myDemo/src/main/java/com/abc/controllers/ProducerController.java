package com.abc.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.model.PublishToTopicRequest;
import com.abc.producer.ProducerProxy;
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
			ZookeeperProxy z = new ZookeeperProxy();
			List<String> l = z.getListOfTopics();
			log.warn("zzzz list of topic:"+z);
			return "Success";
		} catch (Exception e) {
			return "Failed";
		}

	}

}
