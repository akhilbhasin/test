package com.abc.controllers;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dao.GroupIdToSubscriptionsDao;
import com.abc.model.GetMessagesRequest;
import com.abc.model.ListTopicRequest;
import com.abc.model.RemoveTopicForGroupIdRequest;
import com.abc.model.ScheduleConsumerRequest;
import com.abc.model.SubscribeGroupIdToTopic;
import com.abc.producer.Consumer;
import com.abc.producer.ConsumerProxy;
import com.abc.service.GroupIdToSubscriptionService;
import com.abc.utils.JsonDataAdapter;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	private static Logger log = LoggerFactory.getLogger(ConsumerController.class);
	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
	@Autowired
	private JsonDataAdapter dataAdapter;

	@Autowired
	private GroupIdToSubscriptionsDao dao;
	
	@Autowired
	private GroupIdToSubscriptionService groupIdToSubscriptionService;

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

		ScheduleConsumerRequest request = dataAdapter.read(input, ScheduleConsumerRequest.class);
		if (!map.containsKey(request.getGroupId())) {
			Consumer consumer = new Consumer();
			log.warn("about to schedule");
			consumer.scheduleConsumer(request.getGroupId(), groupIdToSubscriptionService);
		} else {
			log.warn("not scheduling");
			map.put(request.getGroupId(), "Added");
		}
	}

	@RequestMapping("subscribeGroupIdToTopic")
	public void subscribeGroupIdToTopic(@RequestBody final String input) {
		SubscribeGroupIdToTopic request = dataAdapter.read(input, SubscribeGroupIdToTopic.class);
		dao.addSubscriptionToGroupId(request.getGroupId(), request.getTopic());
	}

	@RequestMapping("removeTopicForGroupId")
	public void removeTopicForGroupId(@RequestBody final String input) {
		RemoveTopicForGroupIdRequest request = dataAdapter.read(input, RemoveTopicForGroupIdRequest.class);
		dao.unsubscribeTopic(request.getGroupId(), request.getTopic());
	}

	@RequestMapping("listTopics")
	public @ResponseBody List<String> getTopicsForGroupId(@RequestBody final String input) {
		ListTopicRequest request = dataAdapter.read(input, ListTopicRequest.class);
		return dao.getSubscriptionsForGroupId(request.getGroupId());
	}

	@RequestMapping("subscribeToTopic")
	public void subscribeToTopic(@RequestBody final String input) {
		SubscribeGroupIdToTopic request = dataAdapter.read(input, SubscribeGroupIdToTopic.class);
		groupIdToSubscriptionService.addSubscription(request.getGroupId(), request.getTopic());
	}
	
	@RequestMapping("unsubscribeToTopic")
	public void unsubscribeTopic(@RequestBody final String input) {
		RemoveTopicForGroupIdRequest request = dataAdapter.read(input, RemoveTopicForGroupIdRequest.class);
		groupIdToSubscriptionService.removeSubscription(request.getGroupId(), request.getTopic());
	}
	
	@RequestMapping("listTopicsForGroup")
	public @ResponseBody List<String> listTopicForGroup(@RequestBody final String input) {
		ListTopicRequest request = dataAdapter.read(input, ListTopicRequest.class);
		return groupIdToSubscriptionService.getSubscriptions(request.getGroupId());
	}
}
